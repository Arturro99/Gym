package pl.lodz.p.it.repositoryhibernate.entity;

import java.time.OffsetDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.lodz.p.it.core.shared.constant.UrlAction;
import pl.lodz.p.it.core.shared.validation.UrlActions;

/**
 * Class responsible for keeping an entity model of the disposable url object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "disposable_url", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"account", "action_type"})
})
@AttributeOverrides(
    {@AttributeOverride(name = "businessId", column = @Column(name = "url", nullable = false, updatable = false, unique = true)),
        @AttributeOverride(name = "creationDate", column = @Column(name = "creation_date", nullable = false)),
        @AttributeOverride(name = "modificationDate", column = @Column(name = "modification_date"))}
)
@EntityListeners(AuditingEntityListener.class)
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
    private OffsetDateTime expireDate;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @LastModifiedBy
    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    private AccountEntity modifiedBy;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @CreatedBy
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private AccountEntity createdBy;
}
