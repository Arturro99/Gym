package pl.lodz.p.it.core.application.secondary.service;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;

import java.time.OffsetDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.application.secondary.mapper.TrainingPlanMapper;
import pl.lodz.p.it.core.domain.TrainingPlan;
import pl.lodz.p.it.core.port.secondary.TrainingPlanRepositoryPort;
import pl.lodz.p.it.core.shared.constant.Level;
import pl.lodz.p.it.core.shared.exception.AccessLevelException;
import pl.lodz.p.it.core.shared.exception.AccountException;
import pl.lodz.p.it.core.shared.exception.TrainingPlanException;
import pl.lodz.p.it.core.shared.exception.TrainingTypeException;
import pl.lodz.p.it.repositoryhibernate.entity.AccessLevelEntity;
import pl.lodz.p.it.repositoryhibernate.entity.AccountEntity;
import pl.lodz.p.it.repositoryhibernate.entity.TrainingPlanEntity;
import pl.lodz.p.it.repositoryhibernate.entity.TrainingTypeEntity;
import pl.lodz.p.it.repositoryhibernate.repository.AccessLevelRepository;
import pl.lodz.p.it.repositoryhibernate.repository.AccountRepository;
import pl.lodz.p.it.repositoryhibernate.repository.TrainingPlanRepository;
import pl.lodz.p.it.repositoryhibernate.repository.TrainingTypeRepository;

/**
 * Service class responsible for operating on training plan repository.
 */
@Service
@Transactional(propagation = MANDATORY, isolation = READ_COMMITTED, timeout = 3)
@Retryable(exclude = {TrainingPlanException.class, AccountException.class},
    maxAttemptsExpression = "${retry.maxAttempts}",
    backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
public class TrainingPlanRepositoryService extends
    BaseRepositoryService<TrainingPlanEntity, TrainingPlan> implements
    TrainingPlanRepositoryPort {

    private final AccountRepository accountRepository;

    private final TrainingTypeRepository trainingTypeRepository;

    private final AccessLevelRepository accessLevelRepository;

    @Autowired
    public TrainingPlanRepositoryService(TrainingPlanRepository repository,
        TrainingPlanMapper mapper,
        AccountRepository accountRepository,
        TrainingTypeRepository trainingTypeRepository,
        AccessLevelRepository accessLevelRepository) {
        super(repository, mapper);
        this.accountRepository = accountRepository;
        this.trainingTypeRepository = trainingTypeRepository;
        this.accessLevelRepository = accessLevelRepository;
    }

    @Override
    public TrainingPlan find(String key) {
        return repository.findByBusinessId(key)
            .map(mapper::toDomainModel)
            .orElseThrow(TrainingPlanException::trainingPlanNotFoundException);
    }

    @Override
    public TrainingPlan save(TrainingPlan trainingPlan) {
        if (repository.findByBusinessId(trainingPlan.getNumber()).isPresent()) {
            throw TrainingPlanException.existingTrainingPlanConflictException();
        }
        TrainingPlanEntity entity = repository.instantiate();
        entity = mapper.toEntityModel(entity, trainingPlan);

        AccountEntity trainer = accountRepository.findByBusinessId(
            trainingPlan.getTrainer().getLogin())
            .orElseThrow(AccountException::accountNotFoundException);
        if (!hasTrainerRole(trainer)) {
            throw AccessLevelException.illegalAccessLevel();
        }
        TrainingTypeEntity trainingType = trainingTypeRepository.findByBusinessId(
            trainingPlan.getTrainingType().getName())
            .orElseThrow(TrainingTypeException::trainingTypeNotFoundException);
        entity.setCreationDate(OffsetDateTime.now());
        entity.setTrainer(trainer);
        entity.setTrainingType(trainingType);

        TrainingPlanEntity saved = repository.save(entity);

        return mapper.toDomainModel(saved);
    }

    @Override
    public TrainingPlan update(String key, TrainingPlan trainingPlan) {
        TrainingPlanEntity entity = repository.findByBusinessId(key).orElseThrow(
            TrainingPlanException::trainingPlanNotFoundException);
        TrainingPlanEntity updated = mapper
            .toEntityModel(entity, trainingPlan);

        if (Optional.ofNullable(trainingPlan.getTrainer()).isPresent()) {
            AccountEntity accountEntity = accountRepository.findByBusinessId(
                trainingPlan.getTrainer().getLogin())
                .orElseThrow(AccountException::accountNotFoundException);
            if (!hasTrainerRole(accountEntity)) {
                throw AccessLevelException.illegalAccessLevel();
            }
            updated.setTrainer(accountEntity);
        }

        if (Optional.ofNullable(trainingPlan.getTrainingType()).isPresent()) {
            TrainingTypeEntity trainingType = trainingTypeRepository.findByBusinessId(
                trainingPlan.getTrainingType().getName())
                .orElseThrow(TrainingTypeException::trainingTypeNotFoundException);
            updated.setTrainingType(trainingType);
        }

        TrainingPlanEntity response = repository.save(updated);
        return mapper.toDomainModel(response);
    }

    @Override
    public void delete(String key) {
        TrainingPlanEntity entity = repository.findByBusinessId(key)
            .orElseThrow(TrainingPlanException::trainingPlanNotFoundException);
        if (!entity.getAccounts().isEmpty()) {
            throw TrainingPlanException.inUseTrainingPlanConflictException();
        }
        repository.delete(entity);
    }

    private boolean hasTrainerRole(AccountEntity accountEntity) {
        return accessLevelRepository.findByAccount(accountEntity).stream()
            .map(AccessLevelEntity::getBusinessId)
            .anyMatch(x -> x.equals(Level.TRAINER.name()));
    }
}
