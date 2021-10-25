package pl.lodz.p.it.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class responsible for keeping a domain model of the diet type object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DietType extends BaseModel {

    private String name;
}
