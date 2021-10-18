package pl.lodz.p.it.repositoryhibernate.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Class responsible for keeping a entity model of the training type object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "training_type")
public class TrainingTypeEntity extends BaseEntity {

    @Column(name = "training_type_name", nullable = false, unique = true)
    private String businessId;
}
