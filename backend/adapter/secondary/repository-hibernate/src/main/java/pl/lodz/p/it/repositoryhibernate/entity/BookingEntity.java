package pl.lodz.p.it.repositoryhibernate.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @NotNull
    private AccountEntity account;

    @ManyToOne
    @JoinColumn(name = "activity", referencedColumnName = "id", updatable = false)
    @NotNull
    private ActivityEntity activity;

    @Column(name = "active", nullable = false)
    @NotNull
    private Boolean active = true;

    @Column(name = "completed", nullable = false)
    @NotNull
    private Boolean completed = false;

    @Column(name = "pending", nullable = false)
    @NotNull
    private Boolean pending = false;
}
