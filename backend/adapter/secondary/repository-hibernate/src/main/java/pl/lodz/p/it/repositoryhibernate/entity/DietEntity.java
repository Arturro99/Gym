package pl.lodz.p.it.repositoryhibernate.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(name = "diet_type", nullable = false, referencedColumnName = "id")
    @NotNull
    private DietTypeEntity dietType;

    @Column(name = "calories", nullable = false)
    @Range(min = 1)
    private Integer calories;

    @Column(name = "meals_number", nullable = false)
    @Range(min = 1)
    private Integer mealsNumber;

    @Column(name = "price", nullable = false)
    @Range(min = 1)
    private Double price;

    @ManyToMany(mappedBy = "diets")
    private Set<AccountEntity> accounts;
}
