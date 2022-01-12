package pl.lodz.p.it.core.application.service;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.application.service.algorithm.GoodsFactorService;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.domain.Diet;
import pl.lodz.p.it.core.domain.TrainingPlan;
import pl.lodz.p.it.core.port.primary.AccountServicePort;
import pl.lodz.p.it.core.port.secondary.AccountRepositoryPort;

/**
 * Service class responsible for operating on account objects.
 */
@Service
@Transactional(propagation = REQUIRES_NEW, isolation = READ_COMMITTED)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService extends BaseService<Account> implements
    AccountServicePort {

    AccountRepositoryPort accountRepositoryPort;

    GoodsFactorService goodsFactorService;

    @Autowired
    public AccountService(AccountRepositoryPort accountRepositoryPort,
        GoodsFactorService goodsFactorService) {
        super(accountRepositoryPort);
        this.accountRepositoryPort = accountRepositoryPort;
        this.goodsFactorService = goodsFactorService;
    }

    @Override
    @Transactional(propagation = REQUIRED, isolation = READ_COMMITTED)
    public Account save(Account account) {
        return super.save(account);
    }

    @Override
    public Account addTrainingPlan(String login, String trainingPlanNumber) {
        float loyaltyFactor = goodsFactorService
            .getIncreasedTrainingPlanPossessingFactor(accountRepositoryPort.find(login));
        return accountRepositoryPort.addTrainingPlan(login, trainingPlanNumber, loyaltyFactor);
    }

    @Override
    public void removeTrainingPlan(String login, String trainingPlanNumber) {
        float loyaltyFactor = goodsFactorService
            .getDecreasedTrainingPlanPossessingFactor(accountRepositoryPort.find(login));
        accountRepositoryPort.removeTrainingPlan(login, trainingPlanNumber, loyaltyFactor);
    }

    @Override
    public Account addDiet(String login, String dietNumber) {
        float loyaltyFactor = goodsFactorService
            .getIncreasedDietPossessingFactor(accountRepositoryPort.find(login));
        return accountRepositoryPort.addDiet(login, dietNumber, loyaltyFactor);
    }

    @Override
    public void removeDiet(String login, String dietNumber) {
        float loyaltyFactor = goodsFactorService
            .getDecreasedDietPossessingFactor(accountRepositoryPort.find(login));
        accountRepositoryPort.removeDiet(login, dietNumber, loyaltyFactor);
    }

    @Override
    public List<Diet> getDietsByAccountLogin(String login) {
        return new ArrayList<>(accountRepositoryPort.getDiets(login));
    }

    @Override
    public List<TrainingPlan> getTrainingPlansByAccountLogin(String login) {
        return new ArrayList<>(accountRepositoryPort.getTrainingPlans(login));
    }
}
