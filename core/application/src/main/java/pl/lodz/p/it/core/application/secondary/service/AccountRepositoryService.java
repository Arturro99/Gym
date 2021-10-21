package pl.lodz.p.it.core.application.secondary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.application.secondary.mapper.AccountMapper;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.port.secondary.AccountRepositoryPort;
import pl.lodz.p.it.repositoryhibernate.entity.AccountEntity;
import pl.lodz.p.it.repositoryhibernate.repository.AccountRepository;

/**
 * Service class responsible for operating on account repository.
 */
@Component
public class AccountRepositoryService extends BaseRepositoryService<AccountEntity, Account> implements
        AccountRepositoryPort {

    @Autowired
    public AccountRepositoryService(AccountRepository repository, AccountMapper mapper) {
        super(repository, mapper);
    }
}
