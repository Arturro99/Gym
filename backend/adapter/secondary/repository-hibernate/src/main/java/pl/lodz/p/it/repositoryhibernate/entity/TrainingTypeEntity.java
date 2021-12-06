package pl.lodz.p.it.repositoryhibernate.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * Class responsible for keeping an entity model of the training type object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "training_type")
@AttributeOverride(name = "businessId", column = @Column(name = "training_type_name", nullable = false, updatable = false, unique = true))
public class TrainingTypeEntity extends BaseEntity {
}
