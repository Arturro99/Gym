package pl.lodz.p.it.repositoryhibernate.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Class responsible for keeping an entity model of the training plan object.
 */
@Getter
@Setter
@Entity
@Table(name = "training_plan")
@AttributeOverride(name = "businessId", column = @Column(name = "number", nullable = false, updatable = false, unique = true))
public class TrainingPlanEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(name = "training_type", nullable = false, referencedColumnName = "id")
    @NotNull
    private TrainingTypeEntity trainingType;

    @Column(name = "personal_trainings_number", nullable = false)
    @Range(min = 0)
    private Integer personalTrainingsNumber;

    @ManyToOne
    @JoinColumn(name = "trainer", nullable = false, referencedColumnName = "id")
    @NotNull
    private AccountEntity trainer;

    @Column(name = "price", nullable = false)
    @Range(min = 1)
    private Double price;

    @ManyToMany(mappedBy = "trainingPlans")
    private Set<AccountEntity> accounts;
}
