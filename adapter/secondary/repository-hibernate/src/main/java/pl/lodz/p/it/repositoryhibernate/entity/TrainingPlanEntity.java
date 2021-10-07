package pl.lodz.p.it.repositoryhibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "training_plan")
public class TrainingPlanEntity extends BaseEntity {

    @Column(name = "number", nullable = false, updatable = false, unique = true)
    private String number;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "training_type", nullable = false, updatable = false, referencedColumnName = "id")
    private TrainingTypeEntity trainingType;

    @Column(name = "personal_trainings_number", nullable = false)
    private Integer calories;

    @ManyToOne
    @JoinColumn(name = "trainer", nullable = false, referencedColumnName = "id")
    private AccountEntity trainer;

    @Column(name = "price", nullable = false)
    private Double price;
}
