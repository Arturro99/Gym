package pl.lodz.p.it.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;

/**
 * Class responsible for keeping a domain model of the diet object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Diet extends BaseModel {

    private String number;

    private String name;

    private DietType dietType;

    private Integer calories;

    private Integer mealsNumber;

    private Double price;
}
