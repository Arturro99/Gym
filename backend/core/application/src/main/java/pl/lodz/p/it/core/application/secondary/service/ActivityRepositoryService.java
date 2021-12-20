package pl.lodz.p.it.core.application.secondary.service;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.application.secondary.mapper.ActivityMapper;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.core.port.secondary.ActivityRepositoryPort;
import pl.lodz.p.it.core.shared.constant.Level;
import pl.lodz.p.it.core.shared.exception.AccessLevelException;
import pl.lodz.p.it.core.shared.exception.AccountException;
import pl.lodz.p.it.core.shared.exception.ActivityException;
import pl.lodz.p.it.repositoryhibernate.entity.AccessLevelEntity;
import pl.lodz.p.it.repositoryhibernate.entity.AccountEntity;
import pl.lodz.p.it.repositoryhibernate.entity.ActivityEntity;
import pl.lodz.p.it.repositoryhibernate.entity.BookingEntity;
import pl.lodz.p.it.repositoryhibernate.repository.AccessLevelRepository;
import pl.lodz.p.it.repositoryhibernate.repository.AccountRepository;
import pl.lodz.p.it.repositoryhibernate.repository.ActivityRepository;
import pl.lodz.p.it.repositoryhibernate.repository.BookingRepository;

/**
 * Service class responsible for operating on activity repository.
 */
@Service
@Transactional(propagation = MANDATORY, isolation = READ_COMMITTED, timeout = 3)
@Retryable(exclude = {ActivityException.class, AccountException.class},
    maxAttemptsExpression = "${retry.maxAttempts}",
    backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ActivityRepositoryService extends
    BaseRepositoryService<ActivityEntity, Activity> implements
    ActivityRepositoryPort {

    AccountRepository accountRepository;

    ActivityRepository activityRepository;

    AccessLevelRepository accessLevelRepository;

    BookingRepository bookingRepository;

    @Autowired
    public ActivityRepositoryService(ActivityRepository repository, ActivityMapper mapper,
        AccountRepository accountRepository, ActivityRepository activityRepository,
        AccessLevelRepository accessLevelRepository,
        BookingRepository bookingRepository) {
        super(repository, mapper);
        this.accountRepository = accountRepository;
        this.activityRepository = activityRepository;
        this.accessLevelRepository = accessLevelRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<Activity> findByTrainer(String login) {
        AccountEntity trainer = accountRepository.findByBusinessId(login)
            .orElseThrow(AccountException::accountNotFoundException);
        return activityRepository.findAllByTrainer(trainer).stream()
            .map(mapper::toDomainModel)
            .collect(Collectors.toList());
    }

    @Override
    public Activity find(String key) {
        return repository.findByBusinessId(key)
            .map(mapper::toDomainModel).orElseThrow(ActivityException::activityNotFoundException);
    }

    @Override
    public Activity save(Activity activity) {
        if (activityRepository.findByBusinessId(activity.getNumber()).isPresent()) {
            throw ActivityException.existingActivityConflictException();
        }
        ActivityEntity entity = repository.instantiate();
        entity = mapper.toEntityModel(entity, activity);

        AccountEntity accountEntity = accountRepository.findByBusinessId(
            activity.getTrainer().getLogin())
            .orElseThrow(AccountException::accountNotFoundException);
        if (!hasTrainerRole(accountEntity)) {
            throw AccessLevelException.illegalAccessLevel();
        }
        entity.setTrainer(accountEntity);

        ActivityEntity saved = repository.save(entity);
        return mapper.toDomainModel(saved);
    }

    @Override
    public Activity update(String key, Activity activity) {
        ActivityEntity entity = repository.findByBusinessId(key).orElseThrow(
            ActivityException::activityNotFoundException);
        if (bookingRepository.findAllByActivity(entity).stream()
            .anyMatch(BookingEntity::getActive) && activity.getCapacity() != null) {
            throw ActivityException.inUseActivityConflictException();
        }
        ActivityEntity updated = mapper
            .toEntityModel(entity, activity);

        if (Optional.ofNullable(activity.getTrainer()).isPresent()) {
            AccountEntity accountEntity = accountRepository.findByBusinessId(
                activity.getTrainer().getLogin())
                .orElseThrow(AccountException::accountNotFoundException);
            if (!hasTrainerRole(accountEntity)) {
                throw AccessLevelException.illegalAccessLevel();
            }
            updated.setTrainer(accountEntity);
        }

        ActivityEntity response = repository.save(updated);
        return mapper.toDomainModel(response);
    }

    @Override
    public void delete(String key) {
        ActivityEntity entity = repository.findByBusinessId(key)
            .orElseThrow(ActivityException::activityNotFoundException);
        if (entity.getActive()) {
            throw ActivityException.activeActivityConflictException();
        }
        repository.delete(entity);
    }

    private boolean hasTrainerRole(AccountEntity accountEntity) {
        return accessLevelRepository.findByAccount(accountEntity).stream()
            .map(AccessLevelEntity::getBusinessId)
            .anyMatch(x -> x.equals(Level.TRAINER.name()));
    }

}
