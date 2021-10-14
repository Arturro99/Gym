package pl.lodz.p.it.core.application.secondary.service;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class AccountService extends BaseService<AccountEntity, Account> implements
    AccountRepositoryPort {

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

}
