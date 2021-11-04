package pl.lodz.p.it.core.application.secondary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.application.secondary.mapper.TrainingPlanMapper;
import pl.lodz.p.it.core.domain.TrainingPlan;
import pl.lodz.p.it.core.port.secondary.TrainingPlanRepositoryPort;
import pl.lodz.p.it.core.shared.exception.AccessLevelException;
import pl.lodz.p.it.core.shared.exception.AccountException;
import pl.lodz.p.it.core.shared.exception.TrainingPlanException;
import pl.lodz.p.it.core.shared.exception.TrainingTypeException;
import pl.lodz.p.it.core.shared.exception.core.BaseException;
import pl.lodz.p.it.repositoryhibernate.entity.AccessLevelEntity;
import pl.lodz.p.it.repositoryhibernate.entity.AccountEntity;
import pl.lodz.p.it.repositoryhibernate.entity.TrainingPlanEntity;
import pl.lodz.p.it.repositoryhibernate.entity.TrainingTypeEntity;
import pl.lodz.p.it.repositoryhibernate.repository.AccessLevelRepository;
import pl.lodz.p.it.repositoryhibernate.repository.AccountRepository;
import pl.lodz.p.it.repositoryhibernate.repository.TrainingPlanRepository;
import pl.lodz.p.it.repositoryhibernate.repository.TrainingTypeRepository;

import java.time.OffsetDateTime;
import java.util.Optional;

/**
 * Service class responsible for operating on training plan repository.
 */
@Component
public class TrainingPlanRepositoryService extends BaseRepositoryService<TrainingPlanEntity, TrainingPlan> implements
        TrainingPlanRepositoryPort {

    private final AccountRepository accountRepository;

    private final TrainingTypeRepository trainingTypeRepository;

    private final AccessLevelRepository accessLevelRepository;

    @Autowired
    public TrainingPlanRepositoryService(TrainingPlanRepository repository, TrainingPlanMapper mapper,
                                         AccountRepository accountRepository,
                                         TrainingTypeRepository trainingTypeRepository,
                                         AccessLevelRepository accessLevelRepository) {
        super(repository, mapper);
        this.accountRepository = accountRepository;
        this.trainingTypeRepository = trainingTypeRepository;
        this.accessLevelRepository = accessLevelRepository;
    }

    @Override
    public TrainingPlan save(TrainingPlan trainingPlan) {
        TrainingPlanEntity entity = repository.instantiate();
        entity = mapper.toEntityModel(entity, trainingPlan);

        AccountEntity trainer = accountRepository.findByBusinessId(
                trainingPlan.getTrainer().getLogin()).orElseThrow(AccountException::accountNotFoundException);
        TrainingTypeEntity trainingType = trainingTypeRepository.findByBusinessId(
                trainingPlan.getTrainingType().getName()).orElseThrow(TrainingTypeException::trainingTypeNotFoundException);
        entity.setCreationDate(OffsetDateTime.now());
        entity.setTrainer(trainer);
        entity.setTrainingType(trainingType);

        TrainingPlanEntity saved = repository.save(entity);

        return mapper.toDomainModel(saved);
    }

    @Override
    public Optional<TrainingPlan> update(String key, TrainingPlan trainingPlan) {
        TrainingPlanEntity entity = repository.findByBusinessId(key).orElseThrow(
                BaseException::notFoundException);
        TrainingPlanEntity updated = mapper
                .toEntityModel(entity, trainingPlan);
        AccountEntity accountEntity = accountRepository.findByBusinessId(
                trainingPlan.getTrainer().getLogin()).orElseThrow(AccountException::accountNotFoundException);
        boolean hasTrainerRole = accessLevelRepository.findByAccount(accountEntity).stream()
                .map(AccessLevelEntity::getBusinessId)
                .anyMatch(x -> x.equals("trainer"));
        if (!hasTrainerRole) {
            throw AccessLevelException.illegalAccessLevel();
        }
        TrainingTypeEntity trainingType = trainingTypeRepository.findByBusinessId(
                trainingPlan.getTrainingType().getName()).orElseThrow(TrainingTypeException::trainingTypeNotFoundException);

        updated.setTrainer(accountEntity);
        updated.setTrainingType(trainingType);
        TrainingPlanEntity response = repository.save(updated);
        return Optional.of(mapper.toDomainModel(response));
    }

    @Override
    public void delete(String key) {
        TrainingPlanEntity entity = repository.findByBusinessId(key)
                .orElseThrow(BaseException::notFoundException);
        if (!entity.getAccounts().isEmpty()) {
            throw TrainingPlanException.trainingPlanConflictException();
        }
        repository.delete(entity);
    }
}
