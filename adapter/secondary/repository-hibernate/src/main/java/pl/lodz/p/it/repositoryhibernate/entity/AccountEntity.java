package pl.lodz.p.it.repositoryhibernate.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Class responsible for keeping a entity model of the account object.
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
    private String email;

    @Column(name = "password", nullable = false)
    @Exclude
    private String password;

    @Column(name = "active", nullable = false)
    @ColumnDefault("true")
    private Boolean active;

    @Column(name = "confirmed", nullable = false)
    @ColumnDefault("false")
    private Boolean confirmed;

    @Column(name = "first_name", table = "account_details", nullable = false)
    private String firstName;

    @Column(name = "last_name", table = "account_details", nullable = false)
    private String lastName;

    @Column(name = "phone_number", table = "account_details", unique = true)
    private String phoneNumber;

    @Column(name = "language", table = "account_details", nullable = false)
    @ColumnDefault("pl")
    private String language;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "modified_by", referencedColumnName = "id", table = "account_details")
    private AccountEntity modifiedBy;

    @Column(name = "last_known_good_login", table = "account_details")
    private LocalDateTime lastKnownGoodLogin;

    @Column(name = "last_known_good_login_ip", table = "account_details")
    private String lastKnownGoodLoginIp;

    @Column(name = "last_known_bad_login", table = "account_details")
    private LocalDateTime lastKnownBadLogin;

    @Column(name = "last_known_bad_login_ip", table = "account_details")
    private String lastKnownBadLoginIp;

    @Column(name = "bad_logins_counter", nullable = false, table = "account_details")
    @ColumnDefault("0")
    private Integer badLoginsCounter;

    @Column(name = "loyalty_factor", nullable = false, table = "account_details")
    private Float loyaltyFactor;

    @Column(name = "gym_member", nullable = false, table = "account_details")
    private Boolean gymMember;

    @ManyToMany
    @JoinTable(
            name = "account_training_plan",
            joinColumns = {@JoinColumn(name = "account")},
            inverseJoinColumns = {@JoinColumn(name = "training_plan")}
    )
    private Set<TrainingPlanEntity> trainingPlans;

    @ManyToMany
    @JoinTable(
            name = "account_diet",
            joinColumns = {@JoinColumn(name = "account")},
            inverseJoinColumns = {@JoinColumn(name = "diet")}
    )
    private Set<DietEntity> diets;
}
