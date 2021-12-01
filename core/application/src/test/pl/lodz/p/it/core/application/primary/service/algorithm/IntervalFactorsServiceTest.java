package pl.lodz.p.it.core.application.primary.service.algorithm;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import pl.lodz.p.it.core.port.secondary.BookingRepositoryPort;

@FieldDefaults(level = AccessLevel.PRIVATE)
class IntervalFactorsServiceTest {

    static final float absenceFactor = 0.3f;
    static final float presenceFactor = 1.5f;
    static final float membershipFactor = 2f;
    static final float loyaltyFactor = 10f;
    static final int activityDuration = 60;

    @Mock
    AccountRepositoryPort accountRepositoryPort;

    @Mock
    BookingRepositoryPort bookingRepositoryPort;

    @InjectMocks
    IntervalFactorsService intervalFactorsService;

    Booking activeAndCompleted;
    Booking activeAndUncompletedAndPendingExpiredBooking;
    Booking activeAndUncompletedAndNotPendingNonExpiredBooking;
    Booking activeAndUncompletedAndNotPendingExpiredBooking;

    Account accountWithActiveAndCompletedBooking;
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
            intervalFactorsService, "presenceFactor", presenceFactor);
        ReflectionTestUtils.setField(
            intervalFactorsService, "membershipFactor", membershipFactor);

        when(accountRepositoryPort.save(any())).thenReturn(null);
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
    void shouldProperlyPunishAndRewardAccountsWhenScheduleActivityPresenceVerificationCalled() {
        //given
        initAccounts();
        initActivities();
        initBookings();
        when(bookingRepositoryPort.findAllByActiveTrueAndCompletedFalse()).thenReturn(
            List.of(activeAndUncompletedAndNotPendingExpiredBooking,
                activeAndUncompletedAndNotPendingNonExpiredBooking,
                activeAndUncompletedAndPendingExpiredBooking));
        when(bookingRepositoryPort.findAllByActiveTrueAndCompletedTrue()).thenReturn(
            List.of(activeAndCompleted));

        //when
        intervalFactorsService.scheduleActivityPresenceVerification();

        //then
        assertAll(() -> {
            assertEquals(loyaltyFactor * presenceFactor,
                accountWithActiveAndCompletedBooking.getLoyaltyFactor());
            assertEquals(loyaltyFactor,
                accountWithActiveAndUncompletedAndPendingBooking.getLoyaltyFactor());
            assertEquals(loyaltyFactor * absenceFactor,
                accountWithActiveAndUncompletedAndNotPendingExpiredBooking.getLoyaltyFactor());
            assertEquals(loyaltyFactor,
                accountWithActiveAndUncompletedAndNotPendingNonExpiredBooking.getLoyaltyFactor());
        });
    }

    void initAccounts() {
        accountWithActiveAndCompletedBooking = Account.builder()
            .login("ActiveAndCompleted")
            .loyaltyFactor(loyaltyFactor)
            .build();

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
            .duration(activityDuration)
            .build();

        nonExpiredActivity = Activity.builder()
            .startDate(OffsetDateTime.now().plus(45, ChronoUnit.MINUTES))
            .duration(activityDuration)
            .build();
    }

    void initBookings() {
        activeAndCompleted = Booking.builder()
            .active(true)
            .completed(true)
            .pending(false)
            .account(accountWithActiveAndCompletedBooking)
            .build();

        activeAndUncompletedAndPendingExpiredBooking = Booking.builder()
            .active(true)
            .completed(false)
            .pending(true)
            .account(accountWithActiveAndUncompletedAndPendingBooking)
            .activity(expiredActivity)
            .build();

        activeAndUncompletedAndNotPendingNonExpiredBooking = Booking.builder()
            .active(true)
            .completed(false)
            .pending(false)
            .account(accountWithActiveAndUncompletedAndNotPendingNonExpiredBooking)
            .activity(nonExpiredActivity)
            .build();

        activeAndUncompletedAndNotPendingExpiredBooking = Booking.builder()
            .active(true)
            .completed(false)
            .pending(false)
            .account(accountWithActiveAndUncompletedAndNotPendingExpiredBooking)
            .activity(expiredActivity)
            .build();
    }
}