package pl.lodz.p.it.repositoryhibernate.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * Class responsible for keeping a entity model of the disposable url object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "disposable_url", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"account", "action_type"})
})
public class DisposableUrlEntity extends BaseEntity {

    @Column(name = "creation_date", nullable = false, updatable = false)
    private final Timestamp creationDate = Timestamp.from(Instant.now());

    @Column(name = "url", nullable = false, updatable = false, unique = true)
    private String businessId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account", nullable = false, updatable = false, referencedColumnName = "id")
    private AccountEntity account;

    @Column(name = "action_type", nullable = false, updatable = false)
    private String actionType;

    @Column(name = "new_email", unique = true)
    private String newEmail;

    @Column(name = "expiration_date", nullable = false)
    private Timestamp expireDate;

    @Column(name = "modification_date")
    private Timestamp modificationDate;

    @ManyToOne
    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    private AccountEntity modifiedBy;

    @ManyToOne
    @JoinColumn(name = "created_by", updatable = false, referencedColumnName = "id")
    private AccountEntity createdBy;
}
