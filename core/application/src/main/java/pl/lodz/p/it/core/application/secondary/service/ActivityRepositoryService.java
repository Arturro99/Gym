package pl.lodz.p.it.core.application.secondary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.application.secondary.mapper.ActivityMapper;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.core.port.secondary.ActivityRepositoryPort;
import pl.lodz.p.it.repositoryhibernate.entity.ActivityEntity;
import pl.lodz.p.it.repositoryhibernate.repository.ActivityRepository;

/**
 * Service class responsible for operating on activity repository.
 */
@Component
public class ActivityRepositoryService extends BaseRepositoryService<ActivityEntity, Activity> implements
        ActivityRepositoryPort {

    @Autowired
    public ActivityRepositoryService(ActivityRepository repository, ActivityMapper mapper) {
        super(repository, mapper);
    }
}
