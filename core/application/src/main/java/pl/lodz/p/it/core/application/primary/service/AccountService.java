package pl.lodz.p.it.core.application.primary.service;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.port.primary.AccountServicePort;
import pl.lodz.p.it.core.port.secondary.AccountRepositoryPort;

/**
 * Service class responsible for operating on account objects.
 */
@Service
@Transactional(propagation = REQUIRES_NEW, isolation = READ_COMMITTED)
public class AccountService extends BaseService<Account> implements
    AccountServicePort {

    private final AccountRepositoryPort accountRepositoryPort;

    @Autowired
    public AccountService(AccountRepositoryPort accountRepositoryPort) {
        super(accountRepositoryPort);
        this.accountRepositoryPort = accountRepositoryPort;
    }

    @Override
    public Account addTrainingPlan(String login, String trainingPlanNumber) {
        return accountRepositoryPort.addTrainingPlan(login, trainingPlanNumber);
    }

    @Override
    public void removeTrainingPlan(String login, String trainingPlanNumber) {
        accountRepositoryPort.removeTrainingPlan(login, trainingPlanNumber);
    }

    @Override
    public Account addDiet(String login, String dietNumber) {
        return accountRepositoryPort.addDiet(login, dietNumber);
    }

    @Override
    public void removeDiet(String login, String dietNumber) {
        accountRepositoryPort.removeDiet(login, dietNumber);
    }
}
