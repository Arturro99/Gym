package pl.lodz.p.it.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;
import pl.lodz.p.it.core.shared.validation.TrainingPlanNumber;

/**
 * Class responsible for keeping a domain model of the training plan object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TrainingPlan extends BaseModel {

    @TrainingPlanNumber
    private String number;

    private String name;

    private TrainingType trainingType;

    @Range(min = 0)
    private Integer personalTrainingsNumber;

    private Account trainer;

    @Range(min = 1)
    private Double price;
}
