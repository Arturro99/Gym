package pl.lodz.p.it.core.application.primary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.port.primary.AccountServicePort;
import pl.lodz.p.it.core.port.secondary.AccountRepositoryPort;

/**
 * Service class responsible for operating on account objects.
 */
@Component
@AllArgsConstructor
public class AccountService extends BaseService<Account> implements
        AccountServicePort {

    private final AccountRepositoryPort accountRepositoryPort;

}
