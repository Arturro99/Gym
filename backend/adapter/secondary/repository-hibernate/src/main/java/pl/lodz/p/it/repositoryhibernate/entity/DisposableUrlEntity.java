package pl.lodz.p.it.repositoryhibernate.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.lodz.p.it.core.shared.constant.UrlAction;
import pl.lodz.p.it.core.shared.validation.UrlActions;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private AccountEntity account;

    @Column(name = "action_type", nullable = false, updatable = false)
    @NotBlank
    @UrlActions(enumClass = UrlAction.class)
    private String actionType;

    @Column(name = "new_email", unique = true)
    @Email(message = "Email is not valid")
    private String newEmail;

    @Column(name = "expiration_date", nullable = false)
    @Future
    private OffsetDateTime expireDate;

    @ManyToOne
    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    @NotNull
    private AccountEntity modifiedBy;

    @ManyToOne
    @JoinColumn(name = "created_by", updatable = false, referencedColumnName = "id")
    @NotNull
    private AccountEntity createdBy;
}
