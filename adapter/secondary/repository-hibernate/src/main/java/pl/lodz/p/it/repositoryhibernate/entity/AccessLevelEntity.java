package pl.lodz.p.it.repositoryhibernate.entity;

import java.sql.Timestamp;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.ColumnDefault;

/**
 * Class responsible for keeping a entity model of the access level object.
 */
@Entity
@Table(name = "access_level", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"account", "level"})
})
public class AccessLevelEntity extends BaseEntity {

    @Column(name = "creation_date", nullable = false, updatable = false)
    private final Timestamp creationDate = Timestamp.from(Instant.now());

    @Column(name = "level", nullable = false, updatable = false, unique = true)
    private String businessId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account", nullable = false, updatable = false, referencedColumnName = "id")
    private AccountEntity account;

    @Column(name = "active", nullable = false)
    @ColumnDefault("true")
    private Boolean active;

    @Column(name = "modification_date")
    private Timestamp modificationDate;

    @ManyToOne
    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    private AccountEntity modifiedBy;

    @ManyToOne
    @JoinColumn(name = "created_by", updatable = false, referencedColumnName = "id")
    private AccountEntity createdBy;
}
