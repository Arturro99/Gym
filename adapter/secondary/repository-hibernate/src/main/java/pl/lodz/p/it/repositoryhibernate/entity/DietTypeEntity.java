package pl.lodz.p.it.repositoryhibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Class responsible for keeping a entity model of the diet type object.
 */
@Entity
@Table(name = "diet_type")
public class DietTypeEntity extends BaseEntity {

    @Column(name = "diet_type_name", nullable = false, unique = true)
    private String businessId;
}