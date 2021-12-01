package pl.lodz.p.it.core.application.primary.service.algorithm;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.port.secondary.AccountRepositoryPort;

/**
 * Service responsible for handling service-side algorithm's factors like assigning/removing diets
 * and training plans.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional(propagation = MANDATORY, isolation = READ_COMMITTED)
@Service
public class GoodsFactorService {

    final AccountRepositoryPort accountRepositoryPort;

    @Value("${algorithm.diet-adding}")
    float dietAddingFactor;

    @Value("${algorithm.diet-removing}")
    float dietRemovingFactor;

    @Value("${algorithm.training-plan-adding}")
    float trainingPlanAddingFactor;

    @Value("${algorithm.training-plan-removing}")
    float trainingPlanRemovingFactor;

    @Autowired
    public GoodsFactorService(
        AccountRepositoryPort accountRepositoryPort) {
        this.accountRepositoryPort = accountRepositoryPort;
    }

    public void increaseDietPossessingFactor(Account account) {
        account.setLoyaltyFactor(account.getLoyaltyFactor() * dietAddingFactor);
        accountRepositoryPort.save(account);
    }

    public void decreaseDietPossessingFactor(Account account) {
        account.setLoyaltyFactor(account.getLoyaltyFactor() * dietRemovingFactor);
        accountRepositoryPort.save(account);
    }

    public void increaseTrainingPlanPossessingFactor(Account account) {
        account.setLoyaltyFactor(account.getLoyaltyFactor() * trainingPlanAddingFactor);
        accountRepositoryPort.save(account);
    }

    public void decreaseTrainingPlanPossessingFactor(Account account) {
        account.setLoyaltyFactor(account.getLoyaltyFactor() * trainingPlanRemovingFactor);
        accountRepositoryPort.save(account);
    }
}
