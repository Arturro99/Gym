package pl.lodz.p.it.repositoryhibernate.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Class responsible for keeping a entity model of the training plan object.
 */
@Getter
@Setter
@Entity
@Table(name = "training_plan")
@AttributeOverride(name = "businessId", column = @Column(name = "number", nullable = false, updatable = false, unique = true))
public class TrainingPlanEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "training_type", nullable = false, referencedColumnName = "id")
    private TrainingTypeEntity trainingType;

    @Column(name = "personal_trainings_number", nullable = false)
    private Integer personalTrainingsNumber;

    @ManyToOne
    @JoinColumn(name = "trainer", nullable = false, referencedColumnName = "id")
    private AccountEntity trainer;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToMany(mappedBy = "trainingPlans")
    private Set<AccountEntity> accounts;
}
