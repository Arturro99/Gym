package pl.lodz.p.it.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.lodz.p.it.core.shared.validation.BookingNumber;

import javax.validation.constraints.NotNull;

/**
 * Class responsible for keeping a domain model of the booking object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Booking extends BaseModel {

    @BookingNumber
    private String number;

    private Account account;

    private Activity activityEntity;

    private Boolean active;

    private Boolean completed;
}
