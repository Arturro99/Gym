package pl.lodz.p.it.core.port.secondary;

import pl.lodz.p.it.core.domain.Account;

/**
 * Interface responsible for integrating account repository with services.
 */
public interface AccountRepositoryPort extends BaseRepositoryPort<Account> {

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
}
