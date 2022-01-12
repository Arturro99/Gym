package pl.lodz.p.it.core.application.service.algorithm;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
class IntervalFactorsServiceTest {

    static final float absenceFactor = 0.3f;
    static final float membershipFactor = 2f;
    static final float loyaltyFactor = 10f;
    static final int activityDuration = 60;

    @Mock
    AccountRepositoryPort accountRepositoryPort;

    @Mock
    BookingRepositoryPort bookingRepositoryPort;

    @Mock
    ActivityRepositoryPort activityRepositoryPort;

    @InjectMocks
    IntervalFactorsService intervalFactorsService;

    Booking activeAndUncompletedAndPendingExpiredBooking;
    Booking activeAndUncompletedAndNotPendingNonExpiredBooking;
    Booking activeAndUncompletedAndNotPendingExpiredBooking;

    Account accountWithActiveAndUncompletedAndPendingBooking;
    Account accountWithActiveAndUncompletedAndNotPendingNonExpiredBooking;
    Account accountWithActiveAndUncompletedAndNotPendingExpiredBooking;

    Activity expiredActivity;
    Activity nonExpiredActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(
            intervalFactorsService, "absenceFactor", absenceFactor);
        ReflectionTestUtils.setField(
            intervalFactorsService, "membershipFactor", membershipFactor);

        initAccounts();
        initActivities();
        initBookings();

        when(accountRepositoryPort.update(any(), any())).thenReturn(null);
        when(bookingRepositoryPort.update(any(), any())).thenReturn(null);
        when(
            activityRepositoryPort
                .find(activeAndUncompletedAndPendingExpiredBooking.getActivity()))
            .thenReturn(expiredActivity);
        when(
            activityRepositoryPort
                .find(activeAndUncompletedAndNotPendingNonExpiredBooking.getActivity()))
            .thenReturn(nonExpiredActivity);
        when(
            activityRepositoryPort
                .find(activeAndUncompletedAndNotPendingExpiredBooking.getActivity()))
            .thenReturn(expiredActivity);
        when(
            accountRepositoryPort
                .find(activeAndUncompletedAndPendingExpiredBooking.getAccount()))
            .thenReturn(accountWithActiveAndUncompletedAndPendingBooking);
        when(
            accountRepositoryPort
                .find(activeAndUncompletedAndNotPendingNonExpiredBooking.getAccount()))
            .thenReturn(accountWithActiveAndUncompletedAndNotPendingNonExpiredBooking);
        when(
            accountRepositoryPort
                .find(activeAndUncompletedAndNotPendingExpiredBooking.getAccount()))
            .thenReturn(accountWithActiveAndUncompletedAndNotPendingExpiredBooking);
    }

    @Test
    void shouldProperlyIncreaseLoyaltyFactorWhenScheduleGymMembershipVerificationCalled() {
        //given
        Account accountWithMembership = Account.builder()
            .gymMember(true)
            .loyaltyFactor(loyaltyFactor)
            .build();
        Account accountWithoutMembership = Account.builder()
            .gymMember(false)
            .loyaltyFactor(loyaltyFactor)
            .build();
        when(accountRepositoryPort.findAll())
            .thenReturn(List.of(accountWithMembership, accountWithoutMembership));

        //when
        intervalFactorsService.scheduleGymMembershipVerification();

        //then
        assertAll(() -> {
            assertEquals(loyaltyFactor * membershipFactor,
                accountWithMembership.getLoyaltyFactor());
            assertEquals(loyaltyFactor, accountWithoutMembership.getLoyaltyFactor());
        });
    }

    @Test
    void shouldProperlyPunishAccountsWhenScheduleActivityAbsenceVerificationCalled() {
        //given
        when(bookingRepositoryPort.findAllByActiveTrueAndCompletedFalse()).thenReturn(
            List.of(activeAndUncompletedAndNotPendingExpiredBooking,
                activeAndUncompletedAndNotPendingNonExpiredBooking,
                activeAndUncompletedAndPendingExpiredBooking));

        //when
        intervalFactorsService.scheduleActivityAbsenceVerification();

        //then
        assertAll(() -> {
            assertEquals(loyaltyFactor,
                accountWithActiveAndUncompletedAndPendingBooking.getLoyaltyFactor());
            assertEquals(loyaltyFactor * absenceFactor,
                accountWithActiveAndUncompletedAndNotPendingExpiredBooking.getLoyaltyFactor());
            assertEquals(loyaltyFactor,
                accountWithActiveAndUncompletedAndNotPendingNonExpiredBooking.getLoyaltyFactor());
            assertFalse(activeAndUncompletedAndNotPendingExpiredBooking.getActive());
            assertTrue(activeAndUncompletedAndPendingExpiredBooking.getActive());
            assertTrue(activeAndUncompletedAndNotPendingNonExpiredBooking.getActive());
        });
    }

    void initAccounts() {
        accountWithActiveAndUncompletedAndPendingBooking = Account.builder()
            .login("ActiveAndUncompletedAndPending")
            .loyaltyFactor(loyaltyFactor)
            .build();

        accountWithActiveAndUncompletedAndNotPendingExpiredBooking = Account.builder()
            .login("ActiveAndUncompletedAndNotPendingExpired")
            .loyaltyFactor(loyaltyFactor)
            .build();

        accountWithActiveAndUncompletedAndNotPendingNonExpiredBooking = Account.builder()
            .login("ActiveAndUncompletedAndNotPendingNonExpired")
            .loyaltyFactor(loyaltyFactor)
            .build();
    }

    void initActivities() {
        expiredActivity = Activity.builder()
            .startDate(OffsetDateTime.now().minus(activityDuration + 1, ChronoUnit.MINUTES))
            .number("ACT001")
            .duration(activityDuration)
            .build();

        nonExpiredActivity = Activity.builder()
            .startDate(OffsetDateTime.now().plus(45, ChronoUnit.MINUTES))
            .number("ACT002")
            .duration(activityDuration)
            .build();
    }

    void initBookings() {
        activeAndUncompletedAndPendingExpiredBooking = Booking.builder()
            .active(true)
            .completed(false)
            .pending(true)
            .account(accountWithActiveAndUncompletedAndPendingBooking.getLogin())
            .activity(expiredActivity.getNumber())
            .build();

        activeAndUncompletedAndNotPendingNonExpiredBooking = Booking.builder()
            .active(true)
            .completed(false)
            .pending(false)
            .account(accountWithActiveAndUncompletedAndNotPendingNonExpiredBooking.getLogin())
            .activity(nonExpiredActivity.getNumber())
            .build();

        activeAndUncompletedAndNotPendingExpiredBooking = Booking.builder()
            .active(true)
            .completed(false)
            .pending(false)
            .account(accountWithActiveAndUncompletedAndNotPendingExpiredBooking.getLogin())
            .activity(expiredActivity.getNumber())
            .build();
    }
}