package pl.lodz.p.it.core.application.secondary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.core.application.secondary.mapper.AccessLevelMapper;
import pl.lodz.p.it.core.application.secondary.mapper.AccountMapper;
import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.port.secondary.AccessLevelRepositoryPort;
import pl.lodz.p.it.core.shared.exception.AccessLevelException;
import pl.lodz.p.it.core.shared.exception.AccountException;
import pl.lodz.p.it.repositoryhibernate.entity.AccessLevelEntity;
import pl.lodz.p.it.repositoryhibernate.entity.AccountEntity;
import pl.lodz.p.it.repositoryhibernate.repository.AccessLevelRepository;
import pl.lodz.p.it.repositoryhibernate.repository.AccountRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for operating on access level repository.
 */
@Service
public class AccessLevelRepositoryService extends BaseRepositoryService<AccessLevelEntity, AccessLevel> implements
        AccessLevelRepositoryPort {

    AccessLevelRepository accessLevelRepository;
    AccountRepository accountRepository;
    AccountMapper accountMapper;
    AccessLevelMapper accessLevelMapper;

    @Autowired
    public AccessLevelRepositoryService(AccessLevelRepository accessLevelRepository, AccessLevelMapper accessLevelMapper,
                                        AccountMapper accountMapper, AccountRepository accountRepository) {
        super(accessLevelRepository, accessLevelMapper);
        this.accessLevelRepository = accessLevelRepository;
        this.accountRepository = accountRepository;
        this.accessLevelMapper = accessLevelMapper;
        this.accountMapper = accountMapper;
    }

    @Override
    public List<AccessLevel> findByAccount(Account account) {
        AccountEntity accountEntity = accountRepository.findByBusinessId(
                account.getLogin()).orElseThrow(AccountException::accountNotFoundException);
        return accessLevelRepository.findByAccount(accountEntity).stream()
                .map(accessLevelMapper::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public void removeAccessLevel(String login, String level) {
        AccountEntity account = accountRepository.findByBusinessId(login).orElseThrow(AccountException::accountNotFoundException);
        AccessLevelEntity toDeactivation = accessLevelRepository.findByAccountAndBusinessId(account, level)
                .orElseThrow(AccessLevelException::accessLevelNotFoundException);

        toDeactivation.setActive(false);
        repository.save(toDeactivation);
    }

    @Override
    public AccessLevel save(AccessLevel accessLevel) {
        AccountEntity account = accountRepository.findByBusinessId(
                accessLevel.getAccount().getLogin()).orElseThrow(AccountException::accountNotFoundException);

        AccessLevelEntity accessLevelEntity = accessLevelRepository.findByAccountAndBusinessId(account, accessLevel.getLevel())
                .orElseGet(() -> {
                    AccessLevelEntity newAccessLevel = repository.instantiate();
                    newAccessLevel = mapper.toEntityModel(newAccessLevel, accessLevel);
                    newAccessLevel.setAccount(account);
                    return newAccessLevel;
                });
        accessLevelEntity.setActive(true);
        AccessLevelEntity savedEntity = repository.save(accessLevelEntity);
        return mapper.toDomainModel(savedEntity);
    }
}
