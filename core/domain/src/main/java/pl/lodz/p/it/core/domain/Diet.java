package pl.lodz.p.it.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import pl.lodz.p.it.core.shared.validation.DietNumber;

import javax.validation.constraints.NotNull;

/**
 * Class responsible for keeping a domain model of the diet object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Diet extends BaseModel {

    @DietNumber
    private String number;

    @NotBlank
    private String name;

    @NotNull
    private DietType dietType;

    @NotNull
    @Range(min = 1)
    private Integer calories;

    @NotNull
    @Range(min = 1)
    private Integer mealsNumber;

    @NotNull
    @Range(min = 1)
    private Double price;
}
