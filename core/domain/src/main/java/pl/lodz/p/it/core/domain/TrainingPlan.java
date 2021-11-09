package pl.lodz.p.it.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;
import pl.lodz.p.it.core.shared.validation.TrainingPlanNumber;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Class responsible for keeping a domain model of the training plan object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TrainingPlan extends BaseModel {

    @TrainingPlanNumber
    private String number;

    @NotBlank
    private String name;

    @NotNull
    private TrainingType trainingType;

    @NotNull
    @Range(min = 0)
    private Integer personalTrainingsNumber;

    @NotNull
    private Account trainer;

    @NotNull
    @Range(min = 1)
    private Double price;
}
