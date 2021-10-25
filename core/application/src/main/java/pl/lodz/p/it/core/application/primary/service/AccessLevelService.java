package pl.lodz.p.it.core.application.primary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.core.port.primary.AccessLevelServicePort;
import pl.lodz.p.it.core.port.secondary.AccessLevelRepositoryPort;

/**
 * Service class responsible for operating on access level objects.
 */
@Component
public class AccessLevelService extends BaseService<AccessLevel> implements
        AccessLevelServicePort {

    @Autowired
    public AccessLevelService(AccessLevelRepositoryPort accessLevelRepositoryPort) {
        super(accessLevelRepositoryPort);
    }

}
