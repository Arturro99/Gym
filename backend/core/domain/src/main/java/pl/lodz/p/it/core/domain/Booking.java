package pl.lodz.p.it.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.it.core.shared.validation.BookingNumber;

/**
 * Class responsible for keeping a domain model of the booking object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
public class Booking extends BaseModel {

    @BookingNumber
    private String number;

    private String account;

    private String activity;

    private Boolean active;

    private Boolean completed;

    private Boolean pending;

    private String modifiedBy;

    private String createdBy;
}
