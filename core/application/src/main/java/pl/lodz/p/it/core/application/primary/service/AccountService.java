package pl.lodz.p.it.core.application.primary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.port.primary.AccountServicePort;
import pl.lodz.p.it.core.port.secondary.AccountRepositoryPort;

/**
 * Service class responsible for operating on account objects.
 */
@Component
public class AccountService extends BaseService<Account> implements
        AccountServicePort {

    @Autowired
    public AccountService(AccountRepositoryPort accountRepositoryPort) {
        super(accountRepositoryPort);
    }

}
