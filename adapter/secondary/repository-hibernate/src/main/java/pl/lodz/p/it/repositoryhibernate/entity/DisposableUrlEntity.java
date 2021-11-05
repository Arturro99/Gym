package pl.lodz.p.it.repositoryhibernate.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.OffsetDateTime;

/**
 * Class responsible for keeping an entity model of the disposable url object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "disposable_url", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"account", "action_type"})
})
@AttributeOverride(name = "businessId", column = @Column(name = "url", nullable = false, updatable = false, unique = true))
public class DisposableUrlEntity extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "account", nullable = false, updatable = false, referencedColumnName = "id")
    private AccountEntity account;

    @Column(name = "action_type", nullable = false, updatable = false)
    private String actionType;

    @Column(name = "new_email", unique = true)
    private String newEmail;

    @Column(name = "expiration_date", nullable = false)
    private OffsetDateTime expireDate;

    @ManyToOne
    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    private AccountEntity modifiedBy;

    @ManyToOne
    @JoinColumn(name = "created_by", updatable = false, referencedColumnName = "id")
    private AccountEntity createdBy;
}
