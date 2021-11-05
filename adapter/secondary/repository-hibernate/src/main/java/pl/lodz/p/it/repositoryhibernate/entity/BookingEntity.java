package pl.lodz.p.it.repositoryhibernate.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * Class responsible for keeping an entity model of the booking object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "booking")
@AttributeOverride(name = "businessId", column = @Column(name = "number", nullable = false, updatable = false, unique = true))
public class BookingEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "account", referencedColumnName = "id", updatable = false)
    private AccountEntity account;

    @ManyToOne
    @JoinColumn(name = "activity", referencedColumnName = "id", updatable = false)
    private ActivityEntity activityEntity;

    @JoinColumn(name = "active", nullable = false)
    @ColumnDefault("true")
    private Boolean active;

    @JoinColumn(name = "active", nullable = false)
    @ColumnDefault("false")
    private Boolean completed;
}
