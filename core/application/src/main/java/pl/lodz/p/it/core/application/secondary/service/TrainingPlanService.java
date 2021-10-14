package pl.lodz.p.it.core.application.secondary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.application.secondary.mapper.TrainingPlanMapper;
import pl.lodz.p.it.core.domain.TrainingPlan;
import pl.lodz.p.it.core.port.secondary.TrainingPlanRepositoryPort;
import pl.lodz.p.it.repositoryhibernate.entity.TrainingPlanEntity;
import pl.lodz.p.it.repositoryhibernate.repository.TrainingPlanRepository;

/**
 * Service class responsible for operating on training plan repository.
 */
@Component
@AllArgsConstructor
public class TrainingPlanService extends BaseService<TrainingPlanEntity, TrainingPlan> implements
    TrainingPlanRepositoryPort {

    private final TrainingPlanRepository trainingPlanRepository;

    private final TrainingPlanMapper trainingPlanMapper;

//    @Override
//    public Optional<Account> find(String key) {
//        return accountRepository.findByLogin(key)
//            .map(accountMapper::toAccount);
//    }
//
//    @Override
//    public List<Account> findAll() {
//        return accountRepository.findAll().stream()
//            .map(accountMapper::toAccount)
//            .collect(Collectors.toList());
//    }
//
//    @Override
//    public Account save(Account account) {
//        AccountEntity accountEntity = accountMapper.toAccountEntity(account);
//        AccountEntity savedEntity = accountRepository.save(accountEntity);
//        return accountMapper.toAccount(savedEntity);
//    }
//
//    @Override
//    public Account update(String key, Account account) {
//        AccountEntity accountEntity = accountRepository.findByLogin(key).orElseThrow(
//            AccountException::accountNotFoundException);
//        AccountEntity updated = accountMapper
//            .toAccountEntity(accountEntity, account);
//        AccountEntity response = accountRepository.save(updated);
//        return accountMapper.toAccount(response);
//    }
//
//    @Override
//    public void delete(String key) {
//        AccountEntity accountEntity = accountRepository.findByLogin(key)
//            .orElseThrow(AccountException::accountNotFoundException);
//        accountRepository.delete(accountEntity);
//    }
}
