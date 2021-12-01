package pl.lodz.p.it.core.application.primary.service;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.application.primary.service.algorithm.GoodsFactorService;
import pl.lodz.p.it.core.domain.Account;
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
    public Account addTrainingPlan(String login, String trainingPlanNumber) {
        goodsFactorService.increaseTrainingPlanPossessingFactor(accountRepositoryPort.find(login));
        return accountRepositoryPort.addTrainingPlan(login, trainingPlanNumber);
    }

    @Override
    public void removeTrainingPlan(String login, String trainingPlanNumber) {
        goodsFactorService.decreaseTrainingPlanPossessingFactor(accountRepositoryPort.find(login));
        accountRepositoryPort.removeTrainingPlan(login, trainingPlanNumber);
    }

    @Override
    public Account addDiet(String login, String dietNumber) {
        goodsFactorService.increaseDietPossessingFactor(accountRepositoryPort.find(login));
        return accountRepositoryPort.addDiet(login, dietNumber);
    }

    @Override
    public void removeDiet(String login, String dietNumber) {
        goodsFactorService.decreaseDietPossessingFactor(accountRepositoryPort.find(login));
        accountRepositoryPort.removeDiet(login, dietNumber);
    }
}
