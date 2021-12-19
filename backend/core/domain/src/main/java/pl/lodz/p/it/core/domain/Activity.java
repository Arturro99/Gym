package pl.lodz.p.it.core.domain;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;
import pl.lodz.p.it.core.shared.validation.ActivityNumber;

/**
 * Class responsible for keeping a domain model of the activity object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class Activity extends BaseModel {

    @ActivityNumber
    private String number;

    private String name;

    @Range(min = 1)
    private Integer duration;

    private OffsetDateTime startDate;

    private Account trainer;

    private Boolean active;

    @Range(min = 1)
    private Integer capacity;

    private Account modifiedBy;

    private Account createdBy;
}
