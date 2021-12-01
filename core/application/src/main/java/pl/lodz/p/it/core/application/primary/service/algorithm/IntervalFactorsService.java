package pl.lodz.p.it.core.application.primary.service.algorithm;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.domain.Booking;
import pl.lodz.p.it.core.port.secondary.AccountRepositoryPort;
import pl.lodz.p.it.core.port.secondary.BookingRepositoryPort;

/**
 * Service responsible for handling interval-side algorithm's factors like presence/absence at
 * particular activities and gym membership.
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional(propagation = REQUIRES_NEW, isolation = READ_COMMITTED)
public class IntervalFactorsService {

    final AccountRepositoryPort accountRepositoryPort;

    final BookingRepositoryPort bookingRepositoryPort;

    @Value("${algorithm.activity.absence}")
    float absenceFactor;

    @Value("${algorithm.activity.presence}")
    float presenceFactor;

    @Value("${algorithm.membership}")
    float membershipFactor;

    @Autowired
    public IntervalFactorsService(AccountRepositoryPort accountRepositoryPort,
        BookingRepositoryPort bookingRepositoryPort) {

        this.accountRepositoryPort = accountRepositoryPort;
        this.bookingRepositoryPort = bookingRepositoryPort;
    }

    /**
     * Scheduled method responsible for checking whether user is a gym member. And if so, he is
     * rewarded by increasing his loyalty factor.
     */
    @Scheduled(cron = "${algorithm.cron.membership}")
    public void scheduleGymMembershipVerification() {
        List<Account> accounts = accountRepositoryPort.findAll();

        accounts.stream()
            .filter(Account::getGymMember)
            .forEach(account -> {
                    account.setLoyaltyFactor(account.getLoyaltyFactor() * membershipFactor);
                    accountRepositoryPort.save(account);
                }
            );
    }

    /**
     * Scheduled method responsible for checking whether user participated in assigned to his
     * bookings activities and punishing/rewarding him by decreasing/increasing his loyalty factor.
     */
    @Scheduled(cron = "${algorithm.cron.presence}")
    public void scheduleActivityPresenceVerification() {
        punishAccounts();
        rewardAccounts();
    }

    //Method responsible for decreasing loyalty factor for accounts that have active and not completed
    //booking with expired activity.
    private void punishAccounts() {
        List<Booking> activeAndNotCompletedBookings = bookingRepositoryPort
            .findAllByActiveTrueAndCompletedFalse();

        List<Booking> bookingsWithAbsence = activeAndNotCompletedBookings.stream()
            .filter(booking -> booking.getActivity().getStartDate()
                .plus(booking.getActivity().getDuration(), ChronoUnit.MINUTES)
                .isBefore(OffsetDateTime.now()))
            .filter(booking -> !booking.getPending())
            .collect(Collectors.toList());

        List<Account> accountsToPunish = bookingsWithAbsence.stream()
            .map(Booking::getAccount)
            .collect(Collectors.toList());

        accountsToPunish.forEach(account -> {
                account.setLoyaltyFactor(account.getLoyaltyFactor() * absenceFactor);
                accountRepositoryPort.save(account);
            }
        );
    }

    //Method responsible for increasing loyalty factor for accounts that have active and completed
    //bookings.
    private void rewardAccounts() {
        List<Booking> activeAndCompletedBookings = bookingRepositoryPort
            .findAllByActiveTrueAndCompletedTrue();

        List<Account> accountsToReward = activeAndCompletedBookings.stream()
            .map(Booking::getAccount)
            .collect(Collectors.toList());

        accountsToReward.forEach(account -> {
                account.setLoyaltyFactor(account.getLoyaltyFactor() * presenceFactor);
                accountRepositoryPort.save(account);
            }
        );
    }
}
