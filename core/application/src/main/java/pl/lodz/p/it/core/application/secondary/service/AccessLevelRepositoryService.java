package pl.lodz.p.it.core.application.secondary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.application.secondary.mapper.AccessLevelMapper;
import pl.lodz.p.it.core.domain.AccessLevel;
import pl.lodz.p.it.core.port.secondary.AccessLevelRepositoryPort;
import pl.lodz.p.it.repositoryhibernate.entity.AccessLevelEntity;
import pl.lodz.p.it.repositoryhibernate.repository.AccessLevelRepository;

/**
 * Service class responsible for operating on access level repository.
 */
@Component
public class AccessLevelRepositoryService extends BaseRepositoryService<AccessLevelEntity, AccessLevel> implements
        AccessLevelRepositoryPort {

    @Autowired
    public AccessLevelRepositoryService(AccessLevelRepository repository, AccessLevelMapper mapper) {
        super(repository, mapper);
    }

}
