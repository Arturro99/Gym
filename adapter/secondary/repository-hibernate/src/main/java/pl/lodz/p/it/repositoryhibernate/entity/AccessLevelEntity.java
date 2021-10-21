package pl.lodz.p.it.repositoryhibernate.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Class responsible for keeping a entity model of the access level object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "access_level", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"account", "level"})
})
@AttributeOverride(name = "businessId", column = @Column(name = "level", nullable = false, updatable = false, unique = true))
public class AccessLevelEntity extends BaseEntity {

    @Column(name = "creation_date", nullable = false, updatable = false)
    private final LocalDateTime creationDate = LocalDateTime.now();

    @ManyToOne(optional = false)
    @JoinColumn(name = "account", nullable = false, updatable = false, referencedColumnName = "id")
    private AccountEntity account;

    @Column(name = "active", nullable = false)
    @ColumnDefault("true")
    private Boolean active;

    @Column(name = "modification_date")
    private LocalDateTime modificationDate;

    @ManyToOne
    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    private AccountEntity modifiedBy;

    @ManyToOne
    @JoinColumn(name = "created_by", updatable = false, referencedColumnName = "id")
    private AccountEntity createdBy;
}
