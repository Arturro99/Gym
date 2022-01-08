package pl.lodz.p.it.repositoryhibernate.entity;

import java.time.OffsetDateTime;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString.Exclude;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.lodz.p.it.core.shared.validation.RegexPattern;

/**
 * Class responsible for keeping an entity model of the account object.
 */
@Getter
@Setter
@Entity
@Table(name = "account")
@SecondaryTable(name = "account_details")
@AttributeOverrides(
    {@AttributeOverride(name = "businessId", column = @Column(name = "login", nullable = false, updatable = false, unique = true)),
        @AttributeOverride(name = "creationDate", column = @Column(name = "creation_date", nullable = false, table = "account_details")),
        @AttributeOverride(name = "modificationDate", column = @Column(name = "modification_date", table = "account_details"))}
)
@EntityListeners(AuditingEntityListener.class)
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
    @Size(max = 5)
    @NotNull
    private String language;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @LastModifiedBy
    @JoinColumn(name = "modified_by", referencedColumnName = "id", table = "account_details")
    private AccountEntity modifiedBy;

    @Column(name = "last_known_good_login", table = "account_details")
    @PastOrPresent
    private OffsetDateTime lastKnownGoodLogin;

    @Column(name = "last_known_good_login_ip", table = "account_details")
    @Size(max = 39)
    private String lastKnownGoodLoginIp;

    @Column(name = "last_known_bad_login", table = "account_details")
    @PastOrPresent
    private OffsetDateTime lastKnownBadLogin;

    @Column(name = "last_known_bad_login_ip", table = "account_details")
    @Size(max = 39)
    private String lastKnownBadLoginIp;

    @Column(name = "bad_logins_counter", nullable = false, table = "account_details")
    @Range(min = 0)
    private Integer badLoginsCounter = 0;

    @Column(name = "loyalty_factor", nullable = false, table = "account_details")
    @Positive
    private Float loyaltyFactor = 1F;

    @Column(name = "gym_member", nullable = false, table = "account_details")
    @NotNull
    private Boolean gymMember = false;

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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getBusinessId() != null ? this.getBusinessId().hashCode() : 0);
        return hash;
    }
}
