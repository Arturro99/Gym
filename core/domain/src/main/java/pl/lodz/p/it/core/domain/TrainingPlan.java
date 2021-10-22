package pl.lodz.p.it.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;

/**
 * Class responsible for keeping a domain model of the training plan object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TrainingPlan extends BaseModel {

    private String number;

    private String name;

    private TrainingType trainingType;

    private Integer personalTrainingsNumber;

    private Account trainer;

    private Double price;

    private HashSet<Account> accounts;
}
