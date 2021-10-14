package pl.lodz.p.it.repositoryhibernate.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.ColumnDefault;

/**
 * Class responsible for keeping a entity model of the activity object.
 */
@Entity
@Table(name = "activity")
public class ActivityEntity extends BaseEntity {

    @Column(name = "number", nullable = false, updatable = false, unique = true)
    private String businessId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_date", nullable = false)
    private Timestamp creationDate;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "trainer", nullable = false, referencedColumnName = "id")
    private AccountEntity trainer;

    @Column(name = "active", nullable = false)
    @ColumnDefault("true")
    private Boolean active;
}
