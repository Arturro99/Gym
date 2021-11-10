package pl.lodz.p.it.repositoryhibernate.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Range;
import pl.lodz.p.it.core.shared.validation.RegexPattern;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.Set;

/**
 * Class responsible for keeping an entity model of the account object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "account")
@SecondaryTable(name = "account_details")
@AttributeOverrides(
        {@AttributeOverride(name = "businessId", column = @Column(name = "login", nullable = false, updatable = false, unique = true)),
                @AttributeOverride(name = "creationDate", column = @Column(name = "creation_date", nullable = false, table = "account_details")),
                @AttributeOverride(name = "modificationDate", column = @Column(name = "modification_date", table = "account_details"))}
)
@ToString
public class AccountEntity extends BaseEntity {

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Email is not valid")
    @NotNull
    private String email;

    @Column(name = "password", nullable = false)
    @Exclude
    @NotNull
    @Size(min = 60, max = 60, message = "Password must have exactly 60 characters")
    private String password;

    @Column(name = "active", nullable = false)
    @NotNull
    private Boolean active = true;

    @Column(name = "confirmed", nullable = false)
    @NotNull
    private Boolean confirmed = false;

    @Column(name = "first_name", table = "account_details", nullable = false)
    @NotNull
    private String firstName;

    @Column(name = "last_name", table = "account_details", nullable = false)
    @NotNull
    private String lastName;

    @Column(name = "phone_number", table = "account_details", unique = true)
    @Pattern(regexp = RegexPattern.PHONE_NUMBER_PATTERN, message = "Invalid phone number")
    private String phoneNumber;

    @Column(name = "language", table = "account_details", nullable = false)
    @NotNull
    private String language;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "modified_by", referencedColumnName = "id", table = "account_details")
    private AccountEntity modifiedBy;

    @Column(name = "last_known_good_login", table = "account_details")
    @Past
    private OffsetDateTime lastKnownGoodLogin;

    @Column(name = "last_known_good_login_ip", table = "account_details")
    @Size(max = 39)
    private String lastKnownGoodLoginIp;

    @Column(name = "last_known_bad_login", table = "account_details")
    @Past
    private OffsetDateTime lastKnownBadLogin;

    @Column(name = "last_known_bad_login_ip", table = "account_details")
    @Size(max = 39)
    private String lastKnownBadLoginIp;

    @Column(name = "bad_logins_counter", nullable = false, table = "account_details")
    @Range(min = 0)
    private Integer badLoginsCounter = 0;

    @Column(name = "loyalty_factor", nullable = false, table = "account_details")
    @Range(min = 1)
    private Float loyaltyFactor = 1F;

    @Column(name = "gym_member", nullable = false, table = "account_details")
    @NotNull
    private Boolean gymMember;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "account_training_plan",
            joinColumns = {@JoinColumn(name = "account")},
            inverseJoinColumns = {@JoinColumn(name = "training_plan")}
    )
    private Set<TrainingPlanEntity> trainingPlans;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "account_diet",
            joinColumns = {@JoinColumn(name = "account")},
            inverseJoinColumns = {@JoinColumn(name = "diet")}
    )
    private Set<DietEntity> diets;
}
