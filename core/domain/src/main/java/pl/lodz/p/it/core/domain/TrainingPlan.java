package pl.lodz.p.it.core.domain;

import java.util.HashSet;

/**
 * Class responsible for keeping a domain model of the training plan object.
 */
public class TrainingPlan {

    private String number;

    private String name;

    private TrainingType trainingType;

    private Integer calories;

    private Account trainer;

    private Double price;

    private HashSet<Account> accounts;
}
