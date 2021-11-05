package pl.lodz.p.it.repositoryhibernate.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Class responsible for keeping an entity model of the diet object.
 */
@Setter
@Getter
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
