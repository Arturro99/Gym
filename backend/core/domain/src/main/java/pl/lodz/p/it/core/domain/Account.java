package pl.lodz.p.it.core.domain;

import java.time.OffsetDateTime;
import java.util.HashSet;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;
import pl.lodz.p.it.core.shared.validation.RegexPattern;

/**
 * Class responsible for keeping a domain model of the account object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class Account extends BaseModel {

    private String login;

    @Email(message = "Email is not valid")
    private String email;

    @Size(min = 60, max = 60)
    private String password;

    private Boolean active;

    private Boolean confirmed;

    private String firstName;

    private String lastName;

    @Pattern(regexp = RegexPattern.PHONE_NUMBER_PATTERN, message = "Invalid phone number")
    private String phoneNumber;

    @Size(max = 5)
    private String language;

    private String modifiedBy;

    private OffsetDateTime lastKnownGoodLogin;

    @Size(max = 39)
    private String lastKnownGoodLoginIp;

    private OffsetDateTime lastKnownBadLogin;

    @Size(max = 39)
    private String lastKnownBadLoginIp;

    @Range(min = 0)
    private Integer badLoginsCounter;

    @Positive
    private Float loyaltyFactor;

    private Boolean gymMember;

    private HashSet<TrainingPlan> trainingPlans;

    private HashSet<Diet> diets;
}
