package pl.lodz.p.it.core.application.primary.service.algorithm;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.domain.Booking;
import pl.lodz.p.it.core.port.secondary.AccountRepositoryPort;
import pl.lodz.p.it.core.port.secondary.BookingRepositoryPort;

/**
 * Service responsible for handling third party-side algorithm factor - presence at activities.
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional(propagation = REQUIRES_NEW, isolation = READ_COMMITTED)
public class ThirdPartyFactorsService {

    final AccountRepositoryPort accountRepositoryPort;

    final BookingRepositoryPort bookingRepositoryPort;

    @Value("${algorithm.activity.presence}")
    float presenceFactor;

    @Autowired
    public ThirdPartyFactorsService(AccountRepositoryPort accountRepositoryPort,
        BookingRepositoryPort bookingRepositoryPort) {

        this.accountRepositoryPort = accountRepositoryPort;
        this.bookingRepositoryPort = bookingRepositoryPort;
    }

    /**
     * Method responsible for rewarding the particular account for participating in already finished
     * activity by increasing its loyalty factor.
     *
     * @param account Account that completed the booking
     */
    public void rewardAccount(Account account) {
        Account updatedAccount = Account.builder()
            .loyaltyFactor(account.getLoyaltyFactor() * presenceFactor)
            .build();
        accountRepositoryPort.update(account.getLogin(), updatedAccount);
    }
}
