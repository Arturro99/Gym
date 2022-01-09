package pl.lodz.p.it.core.application.primary.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.lodz.p.it.core.application.primary.service.algorithm.OrderFactorService;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.core.domain.Booking;
import pl.lodz.p.it.core.port.secondary.ActivityRepositoryPort;
import pl.lodz.p.it.core.port.secondary.BookingRepositoryPort;
import pl.lodz.p.it.core.shared.exception.BookingException;

@FieldDefaults(level = AccessLevel.PRIVATE)
class BookingServiceTest {

    final List<Booking> bookings = new ArrayList<>();
    @Mock
    BookingRepositoryPort bookingRepository;
    @Mock
    ActivityRepositoryPort activityRepositoryPort;
    @Mock
    OrderFactorService orderFactorService;
    @InjectMocks
    BookingService bookingService;
    Activity activity;
    Booking highPreferenceBooking;
    Booking midPreferenceBooking;
    Booking lowPreferenceBooking;
    Account accountWithHighPreferenceBooking;
    Account accountWithMidPreferenceBooking;
    Account accountWithLowPreferenceBooking;

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
            .thenReturn(Optional.of(highPreferenceBooking));
        when(bookingRepository
            .findByClientAndActivity(accountWithLowPreferenceBooking.getLogin(),
                activity.getNumber()))
            .thenReturn(Optional.of(lowPreferenceBooking));
        doNothing().when(orderFactorService).calculateBookingOrderFactor(any());
        when(orderFactorService.isActivityFull(any())).thenReturn(false);
    }

    @Test
    void shouldApplyPreferenceAndReturnBookingWhenSaveCalled() {
        //given
        midPreferenceBooking = Booking.builder()
            .account(accountWithMidPreferenceBooking.getLogin())
            .active(false)
            .activity(activity.getNumber())
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
            .thenReturn(Optional.of(midPreferenceBooking));
        when(activityRepositoryPort.find(midPreferenceBooking.getActivity())).thenReturn(activity);
        when(bookingRepository.find(midPreferenceBooking.getNumber()))
            .thenReturn(midPreferenceBooking);
        bookingService.save(midPreferenceBooking);

        //then
        Booking updatedBooking = Booking.builder()
            .account(accountWithMidPreferenceBooking.getLogin())
            .active(true)
            .activity(activity.getNumber())
            .completed(false)
            .pending(true)
            .number("BOO005")
            .build();

        verify(bookingRepository).update(midPreferenceBooking.getNumber(), updatedBooking);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "trainer | true  | false | false | error.booking.clientTrainer.conflict",
        "client  | false | false | false | error.booking.activityInactive.conflict",
        "client  | true  | false | true  | error.booking.cancellation.conflict",
        "client  | true  | true  | false | error.booking.conflict",
    }, delimiter = '|')
    void shouldThrowProvidedExceptionsWhenSaveCalled(final String accountLogin,
        final boolean activityActive, final boolean bookingActive, final boolean bookingCompleted,
        final String errorKey) {

        //given
        Activity activity = Activity.builder()
            .number("ACT001")
            .trainer("trainer")
            .active(activityActive)
            .build();

        Account account = Account.builder()
            .login(accountLogin)
            .build();

        Booking booking = Booking.builder()
            .activity(activity.getNumber())
            .account(account.getLogin())
            .active(bookingActive)
            .completed(bookingCompleted)
            .build();

        when(
            bookingRepository.findByClientAndActivity(account.getLogin(), activity.getNumber()))
            .thenReturn(Optional.of(booking));
        when(activityRepositoryPort.find(activity.getNumber())).thenReturn(activity);

        //then
        BookingException exception = assertThrows(BookingException.class,
            () -> bookingService.save(booking));
        assertEquals(errorKey, exception.getErrorKey());
    }

    private void initActivity() {
        activity = Activity.builder()
            .number("ACT001")
            .trainer("trainer")
            .active(true)
            .capacity(2)
            .build();
    }

    private void initBookings() {
        highPreferenceBooking = Booking.builder()
            .account(accountWithHighPreferenceBooking.getLogin())
            .active(true)
            .activity(activity.getNumber())
            .completed(false)
            .pending(false)
            .number("BOO004")
            .build();

        lowPreferenceBooking = Booking.builder()
            .account(accountWithLowPreferenceBooking.getLogin())
            .active(true)
            .activity(activity.getNumber())
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