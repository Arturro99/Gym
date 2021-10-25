package pl.lodz.p.it.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class responsible for keeping a domain model of the booking object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Booking extends BaseModel {

    private String number;

    private Account account;

    private Activity activityEntity;

    private Boolean active;

    private Boolean completed;
}
