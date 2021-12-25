package pl.lodz.p.it.core.port.primary;

import java.util.List;
import pl.lodz.p.it.core.domain.Account;
import pl.lodz.p.it.core.domain.Booking;
import pl.lodz.p.it.core.domain.Diet;
import pl.lodz.p.it.core.domain.TrainingPlan;

/**
 * Interface responsible for integrating account controller with services.
 */
public interface AccountServicePort extends BaseServicePort<Account> {

    /**
     * Method responsible for assigning a training plan to the user with provided login.
     *
     * @param login              User's business identifier
     * @param trainingPlanNumber Training plan's business identifier
     * @return Updated user's object.
     */
    Account addTrainingPlan(String login, String trainingPlanNumber);

    /**
     * Method responsible for removing a training plan from user's account.
     *
     * @param login              User's business identifier
     * @param trainingPlanNumber Training plan's business identifier
     */
    void removeTrainingPlan(String login, String trainingPlanNumber);

    /**
     * Method responsible for assigning a diet to the user with provided login.
     *
     * @param login      User's business identifier
     * @param dietNumber Diet's business identifier
     * @return Updated user's object.
     */
    Account addDiet(String login, String dietNumber);

    /**
     * Method responsible for removing a diet from user's account.
     *
     * @param login      User's business identifier
     * @param dietNumber Diet's business identifier
     */
    void removeDiet(String login, String dietNumber);

    /**
     * Method responsible for fetching diets for the specific user.
     *
     * @param login User's login
     * @return List of user's diets
     */
    List<Diet> getDietsByAccountLogin(String login);

    /**
     * Method responsible for fetching training plans for the specific user.
     *
     * @param login User's login
     * @return List of user's training plans
     */
    List<TrainingPlan> getTrainingPlansByAccountLogin(String login);
}
