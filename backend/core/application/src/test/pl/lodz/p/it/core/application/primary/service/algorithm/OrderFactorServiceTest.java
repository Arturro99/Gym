package pl.lodz.p.it.core.application.primary.service.algorithm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.core.domain.Booking;
import pl.lodz.p.it.core.port.secondary.AccountRepositoryPort;
import pl.lodz.p.it.core.port.secondary.ActivityRepositoryPort;
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
    @Mock
    ActivityRepositoryPort activityRepositoryPort;
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
        when(activityRepositoryPort.find("ACT001")).thenReturn(fullyOccupiedActivity);
        when(activityRepositoryPort.find("ACT002")).thenReturn(activityWithVacancies);

        when(accountRepositoryPort.find("acc1")).thenReturn(accountWithPendingBooking);
        when(accountRepositoryPort.find("acc2")).thenReturn(accountWithHighPreferenceBooking);
        when(accountRepositoryPort.find("acc3")).thenReturn(accountWithMidPreferenceBooking);
        when(accountRepositoryPort.find("acc4")).thenReturn(accountWithLowPreferenceBooking);

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
        bookings.forEach(booking -> booking.setActivity(activityWithVacancies.getNumber()));
        when(bookingRepositoryPort.findAllByActiveTrueAndCompletedFalse()).thenReturn(bookings);
        //then
        assertFalse(orderFactorService.isActivityFull(activityWithVacancies));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "BOO003 | 1.5 | 1.15",
        "BOO004 | 10  | 1.25",
        "BOO005 | 7.5 | 1.2",
        "BOO006 | 4   | 1.3",
        "BOO998 | 2   | 1.1",
        "BOO999 | 4   | 1.05"
    }, delimiter = '|')
    void shouldProperlySetLoyaltyFactorsWhenCalculateBookingOrderFactorCalled(
        String newBookingNumber, float currentLoyaltyFactor, float expectedMultiplier) {
        //given
        Account additionalAccount1 = Account.builder()
            .login("acc5")
            .loyaltyFactor(2.0f)
            .build();
        Account additionalAccount2 = Account.builder()
            .login("acc6")
            .loyaltyFactor(4.0f)
            .build();

        Booking additionalBooking1 = Booking.builder()
            .number("BOO998")
            .activity(fullyOccupiedActivity.getNumber())
            .account(additionalAccount1.getLogin())
            .creationDate(OffsetDateTime.now().plusDays(4))
            .active(true)
            .build();
        Booking additionalBooking2 = Booking.builder()
            .number("BOO999")
            .activity(fullyOccupiedActivity.getNumber())
            .account(additionalAccount2.getLogin())
            .creationDate(OffsetDateTime.now().plusDays(5))
            .active(true)
            .build();
        bookings.addAll(List.of(additionalBooking1, additionalBooking2));
        List<Account> accounts = new ArrayList<>(Arrays.asList(
            accountWithLowPreferenceBooking, accountWithMidPreferenceBooking,
            accountWithHighPreferenceBooking, accountWithPendingBooking,
            additionalAccount1, additionalAccount2
        ));

        lowPreferenceBooking.setCreationDate(OffsetDateTime.now());
        highPreferenceBooking.setCreationDate(OffsetDateTime.now().plusDays(1));
        midPreferenceBooking.setCreationDate(OffsetDateTime.now().plusDays(2));
        pendingBooking.setCreationDate(OffsetDateTime.now().plusDays(3));

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

        when(accountRepositoryPort.find("acc5")).thenReturn(additionalAccount1);
        when(accountRepositoryPort.find("acc6")).thenReturn(additionalAccount2);

        List<Booking> oldBookings = bookings.stream()
            .filter(booking -> !booking.getNumber().equals(newBookingNumber))
            .collect(Collectors.toList());

        when(bookingRepositoryPort.findByActivity("ACT001")).thenReturn(oldBookings);
        Booking booking = bookings.stream()
            .filter(b -> b.getNumber().equals(newBookingNumber))
            .findFirst().get();

        Account account = accounts.stream()
            .filter(ac -> ac.getLogin().equals(booking.getAccount()))
            .findFirst().get();

        //when
        orderFactorService.calculateBookingOrderFactor(booking);

        //then
        assertEquals(currentLoyaltyFactor * expectedMultiplier, account.getLoyaltyFactor(), DELTA);
    }

    @Test
    void shouldReturnTrueWhenIsActivityFullCalled() {
        //given
        bookings = bookings.subList(0, 3);
        bookings.forEach(booking -> booking.setActivity(activityWithVacancies.getNumber()));
        when(bookingRepositoryPort.findAllByActiveTrueAndCompletedFalse()).thenReturn(bookings);
        //then
        assertTrue(orderFactorService.isActivityFull(activityWithVacancies));
    }

    @Test
    void shouldReturnTrueWhenIsActivityFullWithNewlyAddedBookingCalled() {
        //given
        bookings = bookings.subList(0, 3);
        bookings.forEach(booking -> booking.setActivity(activityWithVacancies.getNumber()));
        Booking newBooking = Booking.builder()
            .number("BOO099")
            .activity(activityWithVacancies.getNumber())
            .active(true)
            .completed(false)
            .build();
        //then
        assertTrue(orderFactorService.isActivityFull(activityWithVacancies, newBooking));
    }

    @AfterEach
    void cleanUp() {
        initActivities();
        initAccounts();
        initBookings();
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
            .account(accountWithPendingBooking.getLogin())
            .active(true)
            .activity(fullyOccupiedActivity.getNumber())
            .completed(false)
            .pending(true)
            .number("BOO003")
            .build();

        highPreferenceBooking = Booking.builder()
            .account(accountWithHighPreferenceBooking.getLogin())
            .active(true)
            .activity(fullyOccupiedActivity.getNumber())
            .completed(false)
            .pending(false)
            .number("BOO004")
            .build();

        midPreferenceBooking = Booking.builder()
            .account(accountWithMidPreferenceBooking.getLogin())
            .active(true)
            .activity(fullyOccupiedActivity.getNumber())
            .completed(false)
            .pending(false)
            .number("BOO005")
            .build();

        lowPreferenceBooking = Booking.builder()
            .account(accountWithLowPreferenceBooking.getLogin())
            .active(true)
            .activity(fullyOccupiedActivity.getNumber())
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