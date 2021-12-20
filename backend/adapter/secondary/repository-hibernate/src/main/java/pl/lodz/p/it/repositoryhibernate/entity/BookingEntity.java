package pl.lodz.p.it.repositoryhibernate.entity;

import static javax.persistence.FetchType.EAGER;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

/**
 * Class responsible for keeping an entity model of the booking object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "booking")
@AttributeOverrides(
    {@AttributeOverride(name = "businessId", column = @Column(name = "number", nullable = false, updatable = false, unique = true)),
        @AttributeOverride(name = "creationDate", column = @Column(name = "creation_date", nullable = false)),
        @AttributeOverride(name = "modificationDate", column = @Column(name = "modification_date"))}
)
public class BookingEntity extends BaseEntity {

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "account", referencedColumnName = "id", updatable = false)
    @NotNull
    private AccountEntity account;

    @ManyToOne(fetch = EAGER)
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

    @ManyToOne(cascade = CascadeType.REFRESH)
    @LastModifiedBy
    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    private AccountEntity modifiedBy;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @CreatedBy
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private AccountEntity createdBy;
}
