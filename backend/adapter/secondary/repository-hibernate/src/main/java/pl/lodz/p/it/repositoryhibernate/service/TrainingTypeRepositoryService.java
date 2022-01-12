package pl.lodz.p.it.repositoryhibernate.service;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.repositoryhibernate.mapper.TrainingTypeMapper;
import pl.lodz.p.it.core.domain.TrainingType;
import pl.lodz.p.it.core.port.secondary.TrainingTypeRepositoryPort;
import pl.lodz.p.it.repositoryhibernate.entity.TrainingTypeEntity;
import pl.lodz.p.it.repositoryhibernate.repository.TrainingTypeRepository;

/**
 * Service class responsible for operating on training type repository.
 */
@Service
@Transactional(propagation = MANDATORY, isolation = READ_COMMITTED, timeout = 3)
@Retryable(maxAttemptsExpression = "${retry.maxAttempts}",
    backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
public class TrainingTypeRepositoryService extends
    BaseRepositoryService<TrainingTypeEntity, TrainingType> implements
    TrainingTypeRepositoryPort {

    @Autowired
    public TrainingTypeRepositoryService(TrainingTypeRepository repository,
        TrainingTypeMapper mapper) {
        super(repository, mapper);
    }
}
