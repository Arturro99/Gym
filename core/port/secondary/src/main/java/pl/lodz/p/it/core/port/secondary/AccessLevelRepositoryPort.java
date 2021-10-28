package pl.lodz.p.it.core.port.secondary;

import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.core.domain.Account;

import java.util.List;

/**
 * Interface responsible for integrating access level repository with services.
 */
public interface AccessLevelRepositoryPort extends BaseRepositoryPort<AccessLevel> {

    List<AccessLevel> findByAccount(Account account);
}
