package pl.lodz.p.it.repositoryhibernate.entity;

import java.sql.Timestamp;
import java.time.Instant;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import lombok.ToString.Exclude;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "account")
@SecondaryTable(name = "account_details")
public class AccountEntity extends BaseEntity {

    @Column(name = "creation_date", nullable = false, table = "account_details")
    private final Timestamp creationDate = Timestamp.from(Instant.now());

    //region Account
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    @Exclude
    private String password;

    @Column(name = "active", nullable = false)
    @ColumnDefault("true")
    private Boolean active;
    //endregion

    @Column(name = "confirmed", nullable = false)
    @ColumnDefault("false")
    private Boolean confirmed;

    //region Account details
    @Column(name = "first_name", table = "account_details", nullable = false)
    private String firstName;

    @Column(name = "last_name", table = "account_details", nullable = false)
    private String lastName;

    @Column(name = "phone_number", table = "account_details", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "language", table = "account_details", nullable = false)
    @ColumnDefault("pl")
    private String language;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "modified_by", referencedColumnName = "id", table = "account_details")
    private AccountEntity modifiedBy;

    @Column(name = "modification_date", table = "account_details")
    private Timestamp modificationDate;

    @Column(name = "last_known_good_login", table = "account_details")
    private Timestamp lastKnownGoodLogin;

    @Column(name = "last_known_good_login_ip", table = "account_details")
    private String lastKnownGoodLoginIp;

    @Column(name = "last_known_bad_login", table = "account_details")
    private Timestamp lastKnownBadLogin;

    @Column(name = "last_known_bad_login_ip", table = "account_details")
    private String lastKnownBadLoginIp;

    @Column(name = "bad_logins_counter", nullable = false, table = "account_details")
    @ColumnDefault("0")
    private Integer badLoginsCounter;

    @Column(name = "loyalty_factor", nullable = false, table = "account_details")
    private Float loyaltyFactor;

    @Column(name = "gym_member", nullable = false, table = "account_details")
    private Boolean gymMember;
    //endregion
}
