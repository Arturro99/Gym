package pl.lodz.p.it.core.port.primary;

import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.core.domain.Account;

import java.util.List;

/**
 * Interface responsible for integrating access level controller with services.
 */
public interface AccessLevelServicePort extends BaseServicePort<AccessLevel> {

    List<AccessLevel> findByAccount(Account account);
}
