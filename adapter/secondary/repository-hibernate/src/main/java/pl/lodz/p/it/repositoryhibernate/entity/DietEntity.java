package pl.lodz.p.it.repositoryhibernate.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

/**
 * Class responsible for keeping a entity model of the diet object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "diet")
@AttributeOverride(name = "businessId", column = @Column(name = "number", nullable = false, updatable = false, unique = true))
public class DietEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "diet_type", nullable = false, updatable = false, referencedColumnName = "id")
    private DietTypeEntity dietType;

    @Column(name = "calories", nullable = false)
    private Integer calories;

    @Column(name = "meals_number", nullable = false)
    private Integer mealsNumber;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToMany(mappedBy = "diets")
    private Set<AccountEntity> accounts;
}
