package pl.lodz.p.it.core.application.secondary.service;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;

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
import pl.lodz.p.it.repositoryhibernate.entity.DisposableUrlEntity;
import pl.lodz.p.it.repositoryhibernate.repository.DisposableUrlRepository;

/**
 * Service class responsible for operating on disposable url repository.
 */
@Service
@Transactional(propagation = MANDATORY, isolation = READ_COMMITTED, timeout = 3)
@Retryable(exclude = {DisposableUrlException.class, AccountException.class},
    maxAttemptsExpression = "${retry.maxAttempts}",
    backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
public class DisposableUrlRepositoryService extends
    BaseRepositoryService<DisposableUrlEntity, DisposableUrl> implements
    DisposableUrlRepositoryPort {

    @Autowired
    public DisposableUrlRepositoryService(DisposableUrlRepository repository,
        DisposableUrlMapper mapper) {
        super(repository, mapper);
    }
}
