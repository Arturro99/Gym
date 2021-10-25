package pl.lodz.p.it.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;

/**
 * Class responsible for keeping a domain model of the activity object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Activity extends BaseModel {

    private String number;

    private String name;

    private Integer duration;

    private OffsetDateTime startDate;

    private Account trainer;

    private Boolean active;
}
