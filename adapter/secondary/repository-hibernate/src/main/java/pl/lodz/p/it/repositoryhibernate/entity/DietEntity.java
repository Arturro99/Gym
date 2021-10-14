package pl.lodz.p.it.repositoryhibernate.entity;

import java.util.HashSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Class responsible for keeping a entity model of the diet object.
 */
@Entity
@Table(name = "diet")
public class DietEntity extends BaseEntity {

    @Column(name = "number", nullable = false, updatable = false, unique = true)
    private String businessId;

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
    private HashSet<AccountEntity> accounts;
}
