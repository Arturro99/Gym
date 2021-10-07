package pl.lodz.p.it.repositoryhibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "training_type")
public class TrainingTypeEntity extends BaseEntity {

    @Column(name = "diet_type_name", nullable = false, unique = true)
    private String name;
}
