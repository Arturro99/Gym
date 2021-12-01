package pl.lodz.p.it.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.lodz.p.it.core.shared.validation.BookingNumber;

/**
 * Class responsible for keeping a domain model of the booking object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class Booking extends BaseModel {

    @BookingNumber
    private String number;

    private Account account;

    private Activity activity;

    private Boolean active;

    private Boolean completed;

    private Boolean pending;
}
