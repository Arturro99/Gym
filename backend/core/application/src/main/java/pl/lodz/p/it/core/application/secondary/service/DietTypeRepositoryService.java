package pl.lodz.p.it.core.application.secondary.service;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.application.secondary.mapper.DietTypeMapper;
import pl.lodz.p.it.core.domain.DietType;
import pl.lodz.p.it.core.port.secondary.DietTypeRepositoryPort;
import pl.lodz.p.it.repositoryhibernate.entity.DietTypeEntity;
import pl.lodz.p.it.repositoryhibernate.repository.DietTypeRepository;

/**
 * Service class responsible for operating on diet type repository.
 */
@Service
@Transactional(propagation = MANDATORY, isolation = READ_COMMITTED, timeout = 3)
@Retryable(maxAttemptsExpression = "${retry.maxAttempts}", backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
public class DietTypeRepositoryService extends
    BaseRepositoryService<DietTypeEntity, DietType> implements
    DietTypeRepositoryPort {

    @Autowired
    public DietTypeRepositoryService(DietTypeRepository repository, DietTypeMapper mapper) {
        super(repository, mapper);
    }
}
