package pl.lodz.p.it.core.application.secondary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.domain.DietType;
import pl.lodz.p.it.core.port.secondary.DietTypeRepositoryPort;
import pl.lodz.p.it.repositoryhibernate.entity.DietTypeEntity;
import pl.lodz.p.it.repositoryhibernate.repository.DietTypeRepository;

/**
 * Service class responsible for operating on diet type repository.
 */
@Component
@AllArgsConstructor
public class DietTypeRepositoryService extends BaseRepositoryService<DietTypeEntity, DietType> implements
    DietTypeRepositoryPort {

    private final DietTypeRepository dietTypeRepository;

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
