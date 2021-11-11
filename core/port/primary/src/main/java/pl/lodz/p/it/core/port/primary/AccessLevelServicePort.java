package pl.lodz.p.it.core.port.primary;

import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.core.domain.Account;

import java.util.List;

/**
 * Interface responsible for integrating access level controller with services.
 */
public interface AccessLevelServicePort extends BaseServicePort<AccessLevel> {

    /**
     * Find access level by account.
     *
     * @param account User's account
     * @return List of access levels that are owned by the user
     */
    List<AccessLevel> findByAccount(Account account);

    /**
     * Make access level with provided login and level inactive.
     *
     * @param login User's login
     * @param level User's access level that is meant to be deactivated
     */
    void removeAccessLevel(String login, String level);
}
