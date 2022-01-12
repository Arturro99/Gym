package pl.lodz.p.it.repositoryhibernate.service;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.repositoryhibernate.mapper.AccessLevelMapper;
import pl.lodz.p.it.repositoryhibernate.mapper.AccountMapper;
import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.port.secondary.AccessLevelRepositoryPort;
import pl.lodz.p.it.core.shared.exception.AccessLevelException;
import pl.lodz.p.it.core.shared.exception.AccountException;
import pl.lodz.p.it.repositoryhibernate.entity.AccessLevelEntity;
import pl.lodz.p.it.repositoryhibernate.entity.AccountEntity;
import pl.lodz.p.it.repositoryhibernate.repository.AccessLevelRepository;
import pl.lodz.p.it.repositoryhibernate.repository.AccountRepository;

/**
 * Service class responsible for operating on access level repository.
 */
@Service
@Transactional(propagation = MANDATORY, isolation = READ_COMMITTED, timeout = 3)
@Retryable(exclude = {AccessLevelException.class, AccountException.class},
    maxAttemptsExpression = "${retry.maxAttempts}",
    backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
public class AccessLevelRepositoryService extends
    BaseRepositoryService<AccessLevelEntity, AccessLevel> implements
    AccessLevelRepositoryPort {

    AccessLevelRepository accessLevelRepository;
    AccountRepository accountRepository;
    AccountMapper accountMapper;
    AccessLevelMapper accessLevelMapper;

    @Autowired
    public AccessLevelRepositoryService(AccessLevelRepository accessLevelRepository,
        AccessLevelMapper accessLevelMapper,
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
            .filter(AccessLevelEntity::getActive)
            .map(accessLevelMapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public void removeAccessLevel(String login, String level) {
        AccountEntity account = accountRepository.findByBusinessId(login)
            .orElseThrow(AccountException::accountNotFoundException);
        AccessLevelEntity toDeactivation = accessLevelRepository
            .findByAccountAndBusinessId(account, level)
            .orElseThrow(AccessLevelException::accessLevelNotFoundException);

        toDeactivation.setActive(false);
        repository.save(toDeactivation);
    }

    @Override
    public AccessLevel save(AccessLevel accessLevel) {
        AccountEntity account = accountRepository.findByBusinessId(
            accessLevel.getAccount())
            .orElseThrow(AccountException::accountNotFoundException);

        AccessLevelEntity accessLevelEntity = accessLevelRepository
            .findByAccountAndBusinessId(account, accessLevel.getLevel())
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
