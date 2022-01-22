package pl.lodz.p.it.core.application.service;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.port.primary.AccessLevelServicePort;
import pl.lodz.p.it.core.port.secondary.AccessLevelRepositoryPort;

/**
 * Service class responsible for operating on access level objects.
 */
@Service
@Transactional(propagation = REQUIRES_NEW, isolation = READ_COMMITTED)
public class AccessLevelService extends BaseService<AccessLevel> implements
    AccessLevelServicePort {

    AccessLevelRepositoryPort accessLevelRepositoryPort;

    @Autowired
    public AccessLevelService(AccessLevelRepositoryPort accessLevelRepositoryPort) {
        super(accessLevelRepositoryPort);
        this.accessLevelRepositoryPort = accessLevelRepositoryPort;
    }

    @Override
    public List<AccessLevel> findByAccount(Account account) {
        return accessLevelRepositoryPort.findByAccount(account);
    }

    @Override
    public void removeAccessLevel(String login, String level) {
        accessLevelRepositoryPort.removeAccessLevel(login, level);
    }
}
