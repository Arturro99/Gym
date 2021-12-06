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
     * @param loyaltyFactor      The value of updated loyalty factor by adding a ne training plan
     * @return Updated user's object.
     */
    Account addTrainingPlan(String login, String trainingPlanNumber, float loyaltyFactor);

    /**
     * Method responsible for removing a training plan from user's account.
     *
     * @param login              User's business identifier
     * @param trainingPlanNumber Training plan's business identifier
     * @param loyaltyFactor      The value of updated loyalty factor by adding a ne training plan
     */
    void removeTrainingPlan(String login, String trainingPlanNumber, float loyaltyFactor);

    /**
     * Method responsible for assigning a diet to the user with provided login.
     *
     * @param login         User's business identifier
     * @param dietNumber    Diet's business identifier
     * @param loyaltyFactor The value of updated loyalty factor by adding a ne training plan
     * @return Updated user's object.
     */
    Account addDiet(String login, String dietNumber, float loyaltyFactor);

    /**
     * Method responsible for removing a diet from user's account.
     *
     * @param login         User's business identifier
     * @param dietNumber    Diet's business identifier
     * @param loyaltyFactor The value of updated loyalty factor by adding a ne training plan
     */
    void removeDiet(String login, String dietNumber, float loyaltyFactor);
}
