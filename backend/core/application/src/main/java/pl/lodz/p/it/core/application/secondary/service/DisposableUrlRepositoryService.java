package pl.lodz.p.it.core.application.secondary.service;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;

import java.time.OffsetDateTime;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.application.secondary.mapper.DisposableUrlMapper;
import pl.lodz.p.it.core.domain.DisposableUrl;
import pl.lodz.p.it.core.port.secondary.DisposableUrlRepositoryPort;
import pl.lodz.p.it.core.shared.exception.AccountException;
import pl.lodz.p.it.core.shared.exception.DisposableUrlException;
import pl.lodz.p.it.repositoryhibernate.entity.AccountEntity;
import pl.lodz.p.it.repositoryhibernate.entity.DisposableUrlEntity;
import pl.lodz.p.it.repositoryhibernate.repository.AccountRepository;
import pl.lodz.p.it.repositoryhibernate.repository.DisposableUrlRepository;

/**
 * Service class responsible for operating on disposable url repository.
 */
@Service
@Transactional(propagation = MANDATORY, isolation = READ_COMMITTED, timeout = 3)
@Retryable(exclude = {DisposableUrlException.class, AccountException.class},
    maxAttemptsExpression = "${retry.maxAttempts}",
    backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class DisposableUrlRepositoryService extends
    BaseRepositoryService<DisposableUrlEntity, DisposableUrl> implements
    DisposableUrlRepositoryPort {

    AccountRepository accountRepository;

    @Autowired
    public DisposableUrlRepositoryService(DisposableUrlRepository repository,
        DisposableUrlMapper mapper,
        AccountRepository accountRepository) {
        super(repository, mapper);
        this.accountRepository = accountRepository;
    }

    @Override
    public DisposableUrl find(String key) {
        return repository.findByBusinessId(key)
            .map(mapper::toDomainModel).orElseThrow(DisposableUrlException::urlNotFoundException);
    }

    @Override
    public DisposableUrl save(DisposableUrl disposableUrl) {
        if (repository.findByBusinessId(disposableUrl.getUrl()).isPresent() &&
            repository.findByBusinessId(disposableUrl.getUrl()).get()
                .getExpireDate().isAfter(OffsetDateTime.now())) {
            throw DisposableUrlException.urlConflictException();
        }
        DisposableUrlEntity entity = repository.instantiate();
        entity = mapper.toEntityModel(entity, disposableUrl);

        AccountEntity accountEntity = accountRepository.findAll().stream()
            .filter(account -> account.getBusinessId().equals(disposableUrl.getAccount()))
            .findAny()
            .orElseThrow(AccountException::accountNotFoundException);

        entity.setAccount(accountEntity);
        entity.setCreatedBy(accountEntity);
        entity.setModifiedBy(accountEntity);

        DisposableUrlEntity saved = repository.save(entity);

        return mapper.toDomainModel(saved);
    }
}
