package pl.lodz.p.it.repositoryhibernate.entity;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

/**
 * Class responsible for keeping a entity model of the activity object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "activity")
@AttributeOverride(name = "businessId", column = @Column(name = "number", nullable = false, updatable = false, unique = true))
public class ActivityEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "trainer", nullable = false, referencedColumnName = "id")
    private AccountEntity trainer;

    @Column(name = "active", nullable = false)
    @ColumnDefault("true")
    private Boolean active;
}
