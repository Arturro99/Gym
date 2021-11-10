package pl.lodz.p.it.core.application.primary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.core.port.primary.ActivityServicePort;
import pl.lodz.p.it.core.port.secondary.ActivityRepositoryPort;

import java.util.List;

/**
 * Service class responsible for operating on activity objects.
 */
@Service
public class ActivityService extends BaseService<Activity> implements
        ActivityServicePort {

    private final ActivityRepositoryPort activityRepositoryPort;

    @Autowired
    public ActivityService(ActivityRepositoryPort activityRepositoryPort, ActivityRepositoryPort activityRepositoryPort1) {
        super(activityRepositoryPort);
        this.activityRepositoryPort = activityRepositoryPort1;
    }

    @Override
    public List<Activity> findByTrainer(String login) {
        return activityRepositoryPort.findByTrainer(login);
    }
}
