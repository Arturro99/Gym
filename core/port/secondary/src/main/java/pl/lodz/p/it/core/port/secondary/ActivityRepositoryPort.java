package pl.lodz.p.it.core.port.secondary;

import pl.lodz.p.it.core.domain.Activity;

import java.util.List;

/**
 * Interface responsible for integrating activity repository with services.
 */
public interface ActivityRepositoryPort extends BaseRepositoryPort<Activity> {

    List<Activity> findByTrainer(String login);
}
