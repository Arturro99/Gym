package pl.lodz.p.it.core.port.primary;

import java.util.List;
import pl.lodz.p.it.core.domain.Activity;

/**
 * Interface responsible for integrating activity controller with services.
 */
public interface ActivityServicePort extends BaseServicePort<Activity> {

    List<Activity> findByTrainer(String login);

    /**
     * Method responsble for deactivating activity.
     *
     * @param number Activity's number
     */
    void deactivate(String number);
}
