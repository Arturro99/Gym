package pl.lodz.p.it.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import pl.lodz.p.it.core.shared.validation.ActivityNumber;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

/**
 * Class responsible for keeping a domain model of the activity object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Activity extends BaseModel {

    @ActivityNumber
    private String number;

    @NotBlank
    private String name;

    @NotNull
    @Range(min = 1)
    private Integer duration;

    @NotNull
    private OffsetDateTime startDate;

    @NotNull
    private Account trainer;

    @NotNull
    private Boolean active;
}
