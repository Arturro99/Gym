package pl.lodz.p.it.core.application.primary.service.algorithm;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.core.domain.Booking;
import pl.lodz.p.it.core.port.secondary.AccountRepositoryPort;
import pl.lodz.p.it.core.port.secondary.BookingRepositoryPort;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderFactorServiceTest {

    static final float DELTA = 1e-6f;
    static final float firstOneToBookFactor = 1.3f;
    final static float differenceBetweenNextOnesToBookFactor = 0.05f;

    @Mock
    BookingRepositoryPort bookingRepositoryPort;
    @Mock
    AccountRepositoryPort accountRepositoryPort;
    @InjectMocks
    OrderFactorService orderFactorService;
    Booking pendingBooking;
    Booking highPreferenceBooking;
    Booking midPreferenceBooking;
    Booking lowPreferenceBooking;
    Account accountWithPendingBooking;
    Account accountWithHighPreferenceBooking;
    Account accountWithMidPreferenceBooking;
    Account accountWithLowPreferenceBooking;
    Activity fullyOccupiedActivity;
    Activity activityWithVacancies;
    List<Booking> bookings = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        initActivities();
        initAccounts();
        initBookings();

        bookings.addAll(List.of(
            pendingBooking, highPreferenceBooking,
            midPreferenceBooking, lowPreferenceBooking
        ));
        when(bookingRepositoryPort.findAll()).thenReturn(bookings);
        when(bookingRepositoryPort.findByActivity("ACT001")).thenReturn(bookings);
        when(accountRepositoryPort.save(any())).thenReturn(null);

        ReflectionTestUtils
            .setField(orderFactorService, "firstOneToBookFactor", firstOneToBookFactor);
        ReflectionTestUtils
            .setField(orderFactorService, "differenceBetweenNextOnesToBookFactor",
                differenceBetweenNextOnesToBookFactor);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void shouldReturnFalseWhenIsActivityFullCalled(int numberOfBookings) {
        //given
        bookings = bookings.subList(0, numberOfBookings);
        bookings.forEach(booking -> {
            booking.setCompleted(false);
            booking.setActive(true);
            booking.setActivity(activityWithVacancies);
        });
        //then
        assertFalse(orderFactorService.isActivityFull(activityWithVacancies));
    }

    @Test
    void shouldReturnTrueWhenIsActivityFullCalled() {
        //given
        bookings = bookings.subList(0, 3);
        bookings.forEach(booking -> booking.setActivity(activityWithVacancies));
        //then
        assertTrue(orderFactorService.isActivityFull(activityWithVacancies));
    }

    @Test
    void shouldProperlySetLoyaltyFactorsWhenCalculateBookingOrderFactorCalled() {
        //given
        Booking additionalBooking1 = Booking.builder().build();
        Booking additionalBooking2 = Booking.builder().build();
        bookings.addAll(List.of(additionalBooking1, additionalBooking2));

        Account additionalAccount1 = Account.builder()
            .login("acc5")
            .build();
        Account additionalAccount2 = Account.builder()
            .login("acc6")
            .build();

        additionalAccount1.setLoyaltyFactor(2.0f);
        additionalAccount2.setLoyaltyFactor(4.0f);

        additionalBooking1.setActivity(fullyOccupiedActivity);
        additionalBooking1.setAccount(additionalAccount1);
        additionalBooking2.setActivity(fullyOccupiedActivity);
        additionalBooking2.setAccount(additionalAccount2);

        lowPreferenceBooking.setCreationDate(OffsetDateTime.now());
        highPreferenceBooking.setCreationDate(OffsetDateTime.now().plusDays(1));
        midPreferenceBooking.setCreationDate(OffsetDateTime.now().plusDays(2));
        pendingBooking.setCreationDate(OffsetDateTime.now().plusDays(3));
        additionalBooking1.setCreationDate(OffsetDateTime.now().plusDays(4));
        additionalBooking2.setCreationDate(OffsetDateTime.now().plusDays(5));

        when(bookingRepositoryPort
            .findByClientAndActivity(accountWithLowPreferenceBooking.getLogin(),
                fullyOccupiedActivity.getNumber())).thenReturn(Optional.of(lowPreferenceBooking));
        when(bookingRepositoryPort
            .findByClientAndActivity(accountWithMidPreferenceBooking.getLogin(),
                fullyOccupiedActivity.getNumber())).thenReturn(Optional.of(midPreferenceBooking));
        when(bookingRepositoryPort
            .findByClientAndActivity(accountWithHighPreferenceBooking.getLogin(),
                fullyOccupiedActivity.getNumber())).thenReturn(Optional.of(highPreferenceBooking));
        when(bookingRepositoryPort
            .findByClientAndActivity(accountWithPendingBooking.getLogin(),
                fullyOccupiedActivity.getNumber())).thenReturn(Optional.of(pendingBooking));
        when(bookingRepositoryPort
            .findByClientAndActivity(additionalAccount1.getLogin(),
                fullyOccupiedActivity.getNumber())).thenReturn(Optional.of(additionalBooking1));
        when(bookingRepositoryPort
            .findByClientAndActivity(additionalAccount2.getLogin(),
                fullyOccupiedActivity.getNumber())).thenReturn(Optional.of(additionalBooking2));

        List<Account> accounts = bookings.stream()
            .map(Booking::getAccount)
            .collect(Collectors.toList());

        //when
        accounts.forEach(account ->
            orderFactorService.calculateBookingOrderFactor(bookings.stream()
                .filter(booking -> booking.getAccount().getLogin().equals(account.getLogin()))
                .findFirst().get()));

        //then
        assertAll(() -> {
            assertEquals(4f * 1.3f, accountWithLowPreferenceBooking.getLoyaltyFactor(), DELTA);
            assertEquals(10f * 1.25f, accountWithHighPreferenceBooking.getLoyaltyFactor(), DELTA);
            assertEquals(7.5f * 1.2f, accountWithMidPreferenceBooking.getLoyaltyFactor(), DELTA);
            assertEquals(1.5f * 1.15f, accountWithPendingBooking.getLoyaltyFactor(), DELTA);
            assertEquals(2.0f * 1.1f, additionalAccount1.getLoyaltyFactor(), DELTA);
            assertEquals(4.0f * 1.05f, additionalAccount2.getLoyaltyFactor(), DELTA);
        });
    }

    private void initActivities() {
        fullyOccupiedActivity = Activity.builder()
            .number("ACT001")
            .capacity(2)
            .build();

        activityWithVacancies = Activity.builder()
            .number("ACT002")
            .capacity(3)
            .build();
    }

    private void initBookings() {
        pendingBooking = Booking.builder()
            .account(accountWithPendingBooking)
            .active(true)
            .activity(fullyOccupiedActivity)
            .completed(false)
            .pending(true)
            .number("BOO003")
            .build();

        highPreferenceBooking = Booking.builder()
            .account(accountWithHighPreferenceBooking)
            .active(true)
            .activity(fullyOccupiedActivity)
            .completed(false)
            .pending(false)
            .number("BOO004")
            .build();

        midPreferenceBooking = Booking.builder()
            .account(accountWithMidPreferenceBooking)
            .active(true)
            .activity(fullyOccupiedActivity)
            .completed(false)
            .pending(false)
            .number("BOO005")
            .build();

        lowPreferenceBooking = Booking.builder()
            .account(accountWithLowPreferenceBooking)
            .active(true)
            .activity(fullyOccupiedActivity)
            .completed(false)
            .pending(false)
            .number("BOO006")
            .build();
    }

    private void initAccounts() {
        accountWithPendingBooking = Account.builder()
            .login("acc1")
            .loyaltyFactor(1.5f)
            .build();

        accountWithHighPreferenceBooking = Account.builder()
            .login("acc2")
            .loyaltyFactor(10f)
            .build();

        accountWithMidPreferenceBooking = Account.builder()
            .login("acc3")
            .loyaltyFactor(7.5f)
            .build();

        accountWithLowPreferenceBooking = Account.builder()
            .login("acc4")
            .loyaltyFactor(4f)
            .build();
    }
}