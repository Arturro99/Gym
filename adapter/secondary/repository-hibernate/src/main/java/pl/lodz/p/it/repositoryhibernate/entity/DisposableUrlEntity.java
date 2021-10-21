package pl.lodz.p.it.repositoryhibernate.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Class responsible for keeping a entity model of the disposable url object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "disposable_url", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"account", "action_type"})
})
@AttributeOverride(name = "businessId", column = @Column(name = "url", nullable = false, updatable = false, unique = true))
public class DisposableUrlEntity extends BaseEntity {

    @Column(name = "creation_date", nullable = false, updatable = false)
    private final LocalDateTime creationDate = LocalDateTime.now();

    @ManyToOne(optional = false)
    @JoinColumn(name = "account", nullable = false, updatable = false, referencedColumnName = "id")
    private AccountEntity account;

    @Column(name = "action_type", nullable = false, updatable = false)
    private String actionType;

    @Column(name = "new_email", unique = true)
    private String newEmail;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expireDate;

    @Column(name = "modification_date")
    private LocalDateTime modificationDate;

    @ManyToOne
    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    private AccountEntity modifiedBy;

    @ManyToOne
    @JoinColumn(name = "created_by", updatable = false, referencedColumnName = "id")
    private AccountEntity createdBy;
}
