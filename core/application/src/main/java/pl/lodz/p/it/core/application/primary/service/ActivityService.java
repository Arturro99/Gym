package pl.lodz.p.it.core.application.primary.service;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.core.port.primary.ActivityServicePort;
import pl.lodz.p.it.core.port.secondary.ActivityRepositoryPort;

/**
 * Service class responsible for operating on activity objects.
 */
@Service
@Transactional(propagation = REQUIRES_NEW, isolation = READ_COMMITTED)
public class ActivityService extends BaseService<Activity> implements
    ActivityServicePort {

    private final ActivityRepositoryPort activityRepositoryPort;

    @Autowired
    public ActivityService(ActivityRepositoryPort activityRepositoryPort,
        ActivityRepositoryPort activityRepositoryPort1) {
        super(activityRepositoryPort);
        this.activityRepositoryPort = activityRepositoryPort1;
    }

    @Override
    public List<Activity> findByTrainer(String login) {
        return activityRepositoryPort.findByTrainer(login);
    }
}
