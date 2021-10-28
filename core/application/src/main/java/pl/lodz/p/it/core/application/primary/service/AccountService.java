package pl.lodz.p.it.core.application.primary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.port.primary.AccountServicePort;
import pl.lodz.p.it.core.port.secondary.AccessLevelRepositoryPort;
import pl.lodz.p.it.core.port.secondary.AccountRepositoryPort;

/**
 * Service class responsible for operating on account objects.
 */
@Service
public class AccountService extends BaseService<Account> implements
        AccountServicePort {

    AccountRepositoryPort accountRepositoryPort;
    AccessLevelRepositoryPort accessLevelRepositoryPort;

    @Autowired
    public AccountService(AccountRepositoryPort accountRepositoryPort, AccessLevelRepositoryPort accessLevelRepositoryPort) {
        super(accountRepositoryPort);
        this.accountRepositoryPort = accountRepositoryPort;
        this.accessLevelRepositoryPort = accessLevelRepositoryPort;
    }
}
