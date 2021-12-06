package pl.lodz.p.it.core.port.primary;

import pl.lodz.p.it.core.domain.Activity;

import java.util.List;

/**
 * Interface responsible for integrating activity controller with services.
 */
public interface ActivityServicePort extends BaseServicePort<Activity> {

    List<Activity> findByTrainer(String login);
}
