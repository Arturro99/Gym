package pl.lodz.p.it.repositoryhibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.ColumnDefault;

/**
 * Class responsible for keeping a entity model of the booking object.
 */
@Entity
@Table(name = "booking")
public class BookingEntity extends BaseEntity {

    @Column(name = "number", nullable = false, updatable = false, unique = true)
    private String businessId;

    @ManyToOne
    @JoinColumn(name = "account", table = "account", referencedColumnName = "id")
    private AccountEntity account;

    @ManyToOne
    @JoinColumn(name = "activity", table = "activity", referencedColumnName = "id")
    private ActivityEntity activityEntity;

    @JoinColumn(name = "active", nullable = false)
    @ColumnDefault("true")
    private Boolean active;

    @JoinColumn(name = "active", nullable = false)
    @ColumnDefault("false")
    private Boolean completed;
}
