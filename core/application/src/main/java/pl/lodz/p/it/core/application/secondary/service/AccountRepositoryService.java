package pl.lodz.p.it.core.application.secondary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.core.application.secondary.mapper.AccessLevelMapper;
import pl.lodz.p.it.core.application.secondary.mapper.AccountMapper;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.domain.UserPrincipal;
import pl.lodz.p.it.core.port.secondary.AccountRepositoryPort;
import pl.lodz.p.it.core.shared.exception.AccountException;
import pl.lodz.p.it.repositoryhibernate.entity.AccessLevelEntity;
import pl.lodz.p.it.repositoryhibernate.entity.AccountEntity;
import pl.lodz.p.it.repositoryhibernate.repository.AccessLevelRepository;
import pl.lodz.p.it.repositoryhibernate.repository.AccountRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for operating on account repository.
 */
@Service
public class AccountRepositoryService extends BaseRepositoryService<AccountEntity, Account> implements
        AccountRepositoryPort, UserDetailsService {

    AccountRepository accountRepository;
    AccessLevelRepository accessLevelRepository;
    AccountMapper accountMapper;
    AccessLevelMapper accessLevelMapper;

    @Autowired
    public AccountRepositoryService(AccountRepository accountRepository, AccountMapper accountMapper, AccessLevelRepository accessLevelRepository,
                                    AccessLevelMapper accessLevelMapper) {
        super(accountRepository, accountMapper);
        this.accountRepository = accountRepository;
        this.accessLevelRepository = accessLevelRepository;
        this.accessLevelMapper = accessLevelMapper;
        this.accountMapper = accountMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final AccountEntity account = accountRepository.findByBusinessId(username).orElseThrow(AccountException::accountNotFoundException);
        final List<AccessLevelEntity> roles = accessLevelRepository.findByAccount(account);
        return new UserPrincipal(accountMapper.toDomainModel(account), roles.stream()
                .map(accessLevelMapper::toDomainModel)
                .collect(Collectors.toList()));
    }
}
