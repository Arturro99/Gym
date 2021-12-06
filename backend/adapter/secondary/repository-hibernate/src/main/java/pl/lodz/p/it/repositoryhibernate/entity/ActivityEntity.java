package pl.lodz.p.it.repositoryhibernate.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

/**
 * Class responsible for keeping an entity model of the activity object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "activity")
@AttributeOverride(name = "businessId", column = @Column(name = "number", nullable = false, updatable = false, unique = true))
public class ActivityEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    @Column(name = "start_date", nullable = false)
    @NotNull
    @Future
    private OffsetDateTime startDate;

    @Column(name = "duration", nullable = false)
    @Range(min = 1)
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "trainer", nullable = false, referencedColumnName = "id")
    @NotNull
    private AccountEntity trainer;

    @Column(name = "active", nullable = false)
    @NotNull
    private Boolean active = true;

    @Column(name = "capacity", nullable = false)
    @NotNull
    private Integer capacity;
}
