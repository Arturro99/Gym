package pl.lodz.p.it.core.application.secondary.service;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.application.secondary.mapper.AccessLevelMapper;
import pl.lodz.p.it.core.application.secondary.mapper.AccountMapper;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.domain.UserPrincipal;
import pl.lodz.p.it.core.port.secondary.AccountRepositoryPort;
import pl.lodz.p.it.core.shared.constant.Level;
import pl.lodz.p.it.core.shared.exception.AccountException;
import pl.lodz.p.it.core.shared.exception.DietException;
import pl.lodz.p.it.core.shared.exception.TrainingPlanException;
import pl.lodz.p.it.repositoryhibernate.entity.AccessLevelEntity;
import pl.lodz.p.it.repositoryhibernate.entity.AccountEntity;
import pl.lodz.p.it.repositoryhibernate.entity.DietEntity;
import pl.lodz.p.it.repositoryhibernate.entity.TrainingPlanEntity;
import pl.lodz.p.it.repositoryhibernate.repository.AccessLevelRepository;
import pl.lodz.p.it.repositoryhibernate.repository.AccountRepository;
import pl.lodz.p.it.repositoryhibernate.repository.DietRepository;
import pl.lodz.p.it.repositoryhibernate.repository.TrainingPlanRepository;

/**
 * Service class responsible for operating on account repository.
 */
@Service
@Transactional(propagation = MANDATORY, isolation = READ_COMMITTED, timeout = 3)
@Retryable(exclude = AccountException.class, maxAttemptsExpression = "${retry.maxAttempts}",
    backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
public class AccountRepositoryService extends
    BaseRepositoryService<AccountEntity, Account> implements
    AccountRepositoryPort, UserDetailsService {

    private final AccountRepository accountRepository;

    private final AccessLevelRepository accessLevelRepository;

    private final TrainingPlanRepository trainingPlanRepository;

    private final DietRepository dietRepository;

    private final AccountMapper accountMapper;

    private final AccessLevelMapper accessLevelMapper;

    @Autowired
    public AccountRepositoryService(AccountRepository accountRepository,
        AccountMapper accountMapper, AccessLevelRepository accessLevelRepository,
        TrainingPlanRepository trainingPlanRepository, DietRepository dietRepository,
        AccessLevelMapper accessLevelMapper) {
        super(accountRepository, accountMapper);
        this.accountRepository = accountRepository;
        this.accessLevelRepository = accessLevelRepository;
        this.trainingPlanRepository = trainingPlanRepository;
        this.dietRepository = dietRepository;
        this.accessLevelMapper = accessLevelMapper;
        this.accountMapper = accountMapper;
    }

    @Transactional(propagation = Propagation.NEVER)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final AccountEntity account = accountRepository.findByBusinessId(username)
            .orElseThrow(AccountException::accountNotFoundException);
        final List<AccessLevelEntity> roles = accessLevelRepository.findByAccount(account);
        return new UserPrincipal(accountMapper.toDomainModel(account), roles.stream()
            .map(accessLevelMapper::toDomainModel)
            .collect(Collectors.toList()));
    }

    @Override
    public Account save(Account account) {
        AccountEntity accountEntity = repository.instantiate();
        accountEntity = mapper.toEntityModel(accountEntity, account);
        AccountEntity savedEntity = repository.save(accountEntity);

        AccessLevelEntity accessLevelEntity = accessLevelRepository.instantiate();
        accessLevelEntity.setAccount(accountEntity);
        accessLevelEntity.setBusinessId(Level.CLIENT.name());
        accessLevelEntity.setCreatedBy(accountEntity);
        accessLevelRepository.save(accessLevelEntity);

        return mapper.toDomainModel(savedEntity);
    }

    @Override
    public Account update(String key, Account account) {
        AccountEntity entity = repository.findByBusinessId(key).orElseThrow(
            AccountException::accountNotFoundException);
        AccountEntity updated = mapper
            .toEntityModel(entity, account);
        AccountEntity response = repository.save(updated);
        return mapper.toDomainModel(response);
    }

    @Override
    public Account find(String key) {
        return repository.findByBusinessId(key)
            .map(mapper::toDomainModel).orElseThrow(AccountException::accountNotFoundException);
    }

    @Override
    public Account addTrainingPlan(String login, String trainingPlanNumber, float loyaltyFactor) {
        final AccountEntity account = accountRepository.findByBusinessId(login)
            .orElseThrow(AccountException::accountNotFoundException);
        final TrainingPlanEntity trainingPlan = trainingPlanRepository
            .findByBusinessId(trainingPlanNumber)
            .orElseThrow(TrainingPlanException::trainingPlanNotFoundException);
        if (account.getTrainingPlans().contains(trainingPlan)) {
            throw TrainingPlanException.possessedTrainingPlanConflictException();
        }

        account.getTrainingPlans().add(trainingPlan);
        account.setLoyaltyFactor(loyaltyFactor);
        AccountEntity saved = repository.save(account);
        return mapper.toDomainModel(account);
    }

    @Override
    public void removeTrainingPlan(String login, String trainingPlanNumber, float loyaltyFactor) {
        final AccountEntity account = accountRepository.findByBusinessId(login)
            .orElseThrow(AccountException::accountNotFoundException);
        final TrainingPlanEntity trainingPlan = trainingPlanRepository
            .findByBusinessId(trainingPlanNumber)
            .orElseThrow(TrainingPlanException::trainingPlanNotFoundException);
        if (!account.getTrainingPlans().contains(trainingPlan)) {
            throw TrainingPlanException.trainingPlanNotFoundException();
        }

        account.getTrainingPlans().remove(trainingPlan);
        account.setLoyaltyFactor(loyaltyFactor);
        repository.save(account);
    }

    @Override
    public Account addDiet(String login, String dietNumber, float loyaltyFactor) {
        final AccountEntity account = accountRepository.findByBusinessId(login)
            .orElseThrow(AccountException::accountNotFoundException);
        final DietEntity diet = dietRepository.findByBusinessId(dietNumber)
            .orElseThrow(DietException::dietNotFoundException);
        if (account.getDiets().contains(diet)) {
            throw DietException.possessedDietConflictException();
        }

        account.getDiets().add(diet);
        account.setLoyaltyFactor(loyaltyFactor);
        AccountEntity saved = repository.save(account);
        return mapper.toDomainModel(saved);
    }

    @Override
    public void removeDiet(String login, String dietNumber, float loyaltyFactor) {
        final AccountEntity account = accountRepository.findByBusinessId(login)
            .orElseThrow(AccountException::accountNotFoundException);
        final DietEntity diet = dietRepository.findByBusinessId(dietNumber)
            .orElseThrow(DietException::dietNotFoundException);
        if (!account.getDiets().contains(diet)) {
            throw DietException.dietNotFoundException();
        }

        account.getDiets().remove(diet);
        account.setLoyaltyFactor(loyaltyFactor);
        repository.save(account);
    }
}
