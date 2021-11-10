package pl.lodz.p.it.repositoryhibernate.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;

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
@AttributeOverride(name = "businessId", column = @Column(name = "level", nullable = false, updatable = false, unique = true))
public class AccessLevelEntity extends BaseEntity {

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "account", nullable = false, updatable = false, referencedColumnName = "id")
    @NotNull
    private AccountEntity account;

    @Column(name = "active", nullable = false)
    @ColumnDefault("true")
    @NotNull
    private Boolean active;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    private AccountEntity modifiedBy;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "created_by", updatable = false, referencedColumnName = "id")
    private AccountEntity createdBy;
}
