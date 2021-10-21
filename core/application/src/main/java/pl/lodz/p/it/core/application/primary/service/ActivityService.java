package pl.lodz.p.it.core.application.primary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.core.port.primary.ActivityServicePort;
import pl.lodz.p.it.core.port.secondary.ActivityRepositoryPort;

/**
 * Service class responsible for operating on activity objects.
 */
@Component
public class ActivityService extends BaseService<Activity> implements
        ActivityServicePort {

    @Autowired
    public ActivityService(ActivityRepositoryPort activityRepositoryPort) {
        super(activityRepositoryPort);
    }

}
