package pl.lodz.p.it.repositoryhibernate.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Class responsible for keeping an entity model of the access level object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "access_level", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"account", "level"})
})
@EntityListeners(AuditingEntityListener.class)
@AttributeOverride(name = "businessId", column = @Column(name = "level", nullable = false, updatable = false, unique = true))
public class AccessLevelEntity extends BaseEntity {

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "account", nullable = false, updatable = false, referencedColumnName = "id")
    @NotNull
    private AccountEntity account;

    @Column(name = "active", nullable = false)
    @NotNull
    private Boolean active = true;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @LastModifiedBy
    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    private AccountEntity modifiedBy;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @CreatedBy
    @JoinColumn(name = "created_by", updatable = false, referencedColumnName = "id")
    private AccountEntity createdBy;
}
