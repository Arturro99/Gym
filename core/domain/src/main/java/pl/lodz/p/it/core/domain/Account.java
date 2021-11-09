package pl.lodz.p.it.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import pl.lodz.p.it.core.shared.validation.RegexPattern;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.HashSet;

/**
 * Class responsible for keeping a domain model of the account object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Account extends BaseModel {

    @NotBlank
    private String login;

    @Email(message = "Email is not valid")
    @NotBlank
    private String email;

    @Size(min = 60, max = 60)
    private String password;

    private Boolean active;

    private Boolean confirmed;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Pattern(regexp = RegexPattern.PHONE_NUMBER_PATTERN, message = "Invalid phone number")
    private String phoneNumber;

    @NotBlank
    private String language;

    private Account modifiedBy;

    private OffsetDateTime lastKnownGoodLogin;

    @Size(max = 39)
    private String lastKnownGoodLoginIp;

    private OffsetDateTime lastKnownBadLogin;

    @Size(max = 39)
    private String lastKnownBadLoginIp;

    @Range(min = 0)
    private Integer badLoginsCounter;

    @Range(min = 1)
    private Float loyaltyFactor;

    @NotNull
    private Boolean gymMember;

    private HashSet<TrainingPlan> trainingPlans;

    private HashSet<Diet> diets;
}
