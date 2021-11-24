package pl.lodz.p.it.core.application.primary.service.util;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.core.domain.Booking;
import pl.lodz.p.it.core.port.secondary.BookingRepositoryPort;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class PreferentialAlgorithmTest {

    @Mock
    BookingRepositoryPort bookingRepositoryPort;

    @InjectMocks
    PreferentialAlgorithm algorithm;

    Booking inactiveBooking;
    Booking completedBooking;
    Booking pendingBooking;
    Booking highPreferenceBooking;
    Booking midPreferenceBooking;
    Booking lowPreferenceBooking;

    @Mock
    Account accountWithInactiveBooking;

    @Mock
    Account accountWithCompletedBooking;

    Account accountWithPendingBooking;
    Account accountWithHighPreferenceBooking;
    Account accountWithMidPreferenceBooking;
    Account accountWithLowPreferenceBooking;

    Activity fullyOccupiedActivity;
    Activity activityWithVacancies;

    List<Booking> bookings;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        initActivities();
        initAccounts();
        initBookings();

        bookings = List.of(
            inactiveBooking, completedBooking, pendingBooking,
            highPreferenceBooking, midPreferenceBooking, lowPreferenceBooking
        );
        when(bookingRepositoryPort.findAll()).thenReturn(bookings);
        when(bookingRepositoryPort
            .findByClientAndActivity(accountWithHighPreferenceBooking.getLogin(),
                fullyOccupiedActivity.getNumber()))
            .thenReturn(highPreferenceBooking);
        when(bookingRepositoryPort
            .findByClientAndActivity(accountWithMidPreferenceBooking.getLogin(),
                fullyOccupiedActivity.getNumber()))
            .thenReturn(midPreferenceBooking);
        when(bookingRepositoryPort
            .findByClientAndActivity(accountWithLowPreferenceBooking.getLogin(),
                fullyOccupiedActivity.getNumber()))
            .thenReturn(lowPreferenceBooking);
        when(bookingRepositoryPort
            .findByClientAndActivity(accountWithPendingBooking.getLogin(),
                fullyOccupiedActivity.getNumber()))
            .thenReturn(pendingBooking);
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
        assertFalse(algorithm.isActivityFull(activityWithVacancies));
    }

    @Test
    void shouldReturnTrueWhenIsActivityFullCalled() {
        //given
        bookings = bookings.subList(0, 3);
        bookings.forEach(booking -> {
            booking.setCompleted(false);
            booking.setActive(true);
            booking.setActivity(activityWithVacancies);
        });
        //then
        assertTrue(algorithm.isActivityFull(activityWithVacancies));
    }

    @ParameterizedTest
    @ValueSource(floats = {1.5f, 11f})
    void shouldApplyPreferenceWhenApplyPreferenceCalled(
        float accountWithPendingBookingLoyaltyFactor) {
        //given
        accountWithPendingBooking.setLoyaltyFactor(accountWithPendingBookingLoyaltyFactor);
        //when
        algorithm.applyPreference(fullyOccupiedActivity);

        //then
        assertAll(() -> {
            assertTrue(lowPreferenceBooking.getPending());
            assertFalse(highPreferenceBooking.getPending());

            if (accountWithPendingBookingLoyaltyFactor == 11f) {
                assertFalse(pendingBooking.getPending());
                assertTrue(midPreferenceBooking.getPending());
            } else {
                assertTrue(pendingBooking.getPending());
                assertFalse(midPreferenceBooking.getPending());
            }
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
        inactiveBooking = Booking.builder()
            .account(accountWithInactiveBooking)
            .active(false)
            .activity(fullyOccupiedActivity)
            .completed(false)
            .pending(false)
            .number("BOO001")
            .build();

        completedBooking = Booking.builder()
            .account(accountWithCompletedBooking)
            .active(true)
            .activity(fullyOccupiedActivity)
            .completed(true)
            .pending(false)
            .number("BOO002")
            .build();

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