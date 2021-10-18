package pl.lodz.p.it.core.application.primary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.core.domain.Activity;
import pl.lodz.p.it.core.port.primary.ActivityServicePort;
import pl.lodz.p.it.core.port.secondary.ActivityRepositoryPort;

/**
 * Service class responsible for operating on activity objects.
 */
@Component
@AllArgsConstructor
public class ActivityService extends BaseService<Activity> implements
        ActivityServicePort {

    private final ActivityRepositoryPort activityRepositoryPort;

}
