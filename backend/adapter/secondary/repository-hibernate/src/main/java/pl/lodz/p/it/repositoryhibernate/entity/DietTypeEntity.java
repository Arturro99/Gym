package pl.lodz.p.it.repositoryhibernate.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Class responsible for keeping an entity model of the diet type object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "diet_type")
@AttributeOverride(name = "businessId", column = @Column(name = "diet_type_name", nullable = false, updatable = false, unique = true))
public class DietTypeEntity extends BaseEntity {
}
