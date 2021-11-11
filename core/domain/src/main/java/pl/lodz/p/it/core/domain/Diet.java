package pl.lodz.p.it.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;
import pl.lodz.p.it.core.shared.validation.DietNumber;

/**
 * Class responsible for keeping a domain model of the diet object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Diet extends BaseModel {

    @DietNumber
    private String number;

    private String name;

    private DietType dietType;

    @Range(min = 1)
    private Integer calories;

    @Range(min = 1)
    private Integer mealsNumber;

    @Range(min = 1)
    private Double price;
}
