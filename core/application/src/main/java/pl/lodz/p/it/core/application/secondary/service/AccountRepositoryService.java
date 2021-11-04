package pl.lodz.p.it.core.application.secondary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.core.application.secondary.mapper.AccessLevelMapper;
import pl.lodz.p.it.core.application.secondary.mapper.AccountMapper;
import pl.lodz.p.it.core.application.secondary.mapper.TrainingPlanMapper;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.domain.UserPrincipal;
import pl.lodz.p.it.core.port.secondary.AccountRepositoryPort;
import pl.lodz.p.it.core.shared.exception.AccountException;
import pl.lodz.p.it.core.shared.exception.TrainingPlanException;
import pl.lodz.p.it.repositoryhibernate.entity.AccessLevelEntity;
import pl.lodz.p.it.repositoryhibernate.entity.AccountEntity;
import pl.lodz.p.it.repositoryhibernate.entity.TrainingPlanEntity;
import pl.lodz.p.it.repositoryhibernate.repository.AccessLevelRepository;
import pl.lodz.p.it.repositoryhibernate.repository.AccountRepository;
import pl.lodz.p.it.repositoryhibernate.repository.TrainingPlanRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for operating on account repository.
 */
@Service
public class AccountRepositoryService extends BaseRepositoryService<AccountEntity, Account> implements
        AccountRepositoryPort, UserDetailsService {

    final AccountRepository accountRepository;

    final AccessLevelRepository accessLevelRepository;

    final TrainingPlanRepository trainingPlanRepository;

    final AccountMapper accountMapper;

    final AccessLevelMapper accessLevelMapper;

    final TrainingPlanMapper trainingPlanMapper;

    @Autowired
    public AccountRepositoryService(AccountRepository accountRepository, AccountMapper accountMapper, AccessLevelRepository accessLevelRepository,
                                    TrainingPlanRepository trainingPlanRepository, AccessLevelMapper accessLevelMapper, TrainingPlanMapper trainingPlanMapper) {
        super(accountRepository, accountMapper);
        this.accountRepository = accountRepository;
        this.accessLevelRepository = accessLevelRepository;
        this.trainingPlanRepository = trainingPlanRepository;
        this.accessLevelMapper = accessLevelMapper;
        this.accountMapper = accountMapper;
        this.trainingPlanMapper = trainingPlanMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final AccountEntity account = accountRepository.findByBusinessId(username).orElseThrow(AccountException::accountNotFoundException);
        final List<AccessLevelEntity> roles = accessLevelRepository.findByAccount(account);
        return new UserPrincipal(accountMapper.toDomainModel(account), roles.stream()
                .map(accessLevelMapper::toDomainModel)
                .collect(Collectors.toList()));
    }

    @Override
    public Account addTrainingPlan(String login, String trainingPlanNumber) {
        final AccountEntity account = accountRepository.findByBusinessId(login)
                .orElseThrow(AccountException::accountNotFoundException);
        final TrainingPlanEntity trainingPlan = trainingPlanRepository.findByBusinessId(trainingPlanNumber)
                .orElseThrow(TrainingPlanException::trainingPlanNotFoundException);
        if (account.getTrainingPlans().contains(trainingPlan)) {
            throw TrainingPlanException.trainingPlanConflictException();
        }

        account.getTrainingPlans().add(trainingPlan);
        AccountEntity saved = repository.save(account);
        return mapper.toDomainModel(saved);
    }

    @Override
    public void removeTrainingPlan(String login, String trainingPlanNumber) {
        final AccountEntity account = accountRepository.findByBusinessId(login)
                .orElseThrow(AccountException::accountNotFoundException);
        final TrainingPlanEntity trainingPlan = trainingPlanRepository.findByBusinessId(trainingPlanNumber)
                .orElseThrow(TrainingPlanException::trainingPlanNotFoundException);
        if (!account.getTrainingPlans().contains(trainingPlan)) {
            throw TrainingPlanException.trainingPlanNotFoundException();
        }

        account.getTrainingPlans().remove(trainingPlan);
        repository.save(account);
    }

    @Override
    public Account addDiet(String login, String dietNumber) {
        return null;
    }

    @Override
    public void removeDiet(String login, String dietNumber) {

    }
}
