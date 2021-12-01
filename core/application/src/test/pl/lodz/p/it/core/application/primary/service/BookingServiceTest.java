package pl.lodz.p.it.core.application.primary.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.core.domain.Booking;
import pl.lodz.p.it.core.port.secondary.BookingRepositoryPort;

@FieldDefaults(level = AccessLevel.PRIVATE)
class BookingServiceTest {

    @Mock
    BookingRepositoryPort bookingRepository;

    @InjectMocks
    BookingService bookingService;

    Activity activity;

    Booking highPreferenceBooking;
    Booking midPreferenceBooking;
    Booking lowPreferenceBooking;

    Account accountWithHighPreferenceBooking;
    Account accountWithMidPreferenceBooking;
    Account accountWithLowPreferenceBooking;

    List<Booking> bookings = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        initActivity();
        initAccounts();
        initBookings();

        bookings.addAll(List.of(
            highPreferenceBooking, lowPreferenceBooking
        ));

        when(bookingRepository.findAll()).thenReturn(bookings);
        when(bookingRepository
            .findByClientAndActivity(accountWithHighPreferenceBooking.getLogin(),
                activity.getNumber()))
            .thenReturn(highPreferenceBooking);
        when(bookingRepository
            .findByClientAndActivity(accountWithLowPreferenceBooking.getLogin(),
                activity.getNumber()))
            .thenReturn(lowPreferenceBooking);
    }

    @Test
    void shouldApplyPreferenceAndReturnBookingWhenSaveCalled() {
        //given
        midPreferenceBooking = Booking.builder()
            .account(accountWithMidPreferenceBooking)
            .active(true)
            .activity(activity)
            .completed(false)
            .pending(true)
            .number("BOO005")
            .build();

        //when
        doAnswer(invocation -> {
            bookings.add(invocation.getArgument(0));
            return midPreferenceBooking;
        }).when(bookingRepository).save(midPreferenceBooking);
        when(bookingRepository
            .findByClientAndActivity(accountWithMidPreferenceBooking.getLogin(),
                activity.getNumber()))
            .thenReturn(midPreferenceBooking);
        Booking booking = bookingService.save(midPreferenceBooking);

        //then
        assertAll(() -> {
            assertEquals(midPreferenceBooking.getNumber(), booking.getNumber());
            assertFalse(highPreferenceBooking.getPending());
            assertFalse(midPreferenceBooking.getPending());
            assertTrue(lowPreferenceBooking.getPending());
        });
    }

    private void initActivity() {
        activity = Activity.builder()
            .number("ACT001")
            .capacity(2)
            .build();
    }

    private void initBookings() {
        highPreferenceBooking = Booking.builder()
            .account(accountWithHighPreferenceBooking)
            .active(true)
            .activity(activity)
            .completed(false)
            .pending(false)
            .number("BOO004")
            .build();

        lowPreferenceBooking = Booking.builder()
            .account(accountWithLowPreferenceBooking)
            .active(true)
            .activity(activity)
            .completed(false)
            .pending(false)
            .number("BOO006")
            .build();
    }

    private void initAccounts() {
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