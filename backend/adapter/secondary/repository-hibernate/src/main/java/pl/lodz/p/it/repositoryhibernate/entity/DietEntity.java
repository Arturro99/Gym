package pl.lodz.p.it.repositoryhibernate.entity;

import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

/**
 * Class responsible for keeping an entity model of the diet object.
 */
@Setter
@Getter
@Entity
@Table(name = "diet")
@AttributeOverrides(
    {@AttributeOverride(name = "businessId", column = @Column(name = "number", nullable = false, updatable = false, unique = true)),
        @AttributeOverride(name = "creationDate", column = @Column(name = "creation_date", nullable = false)),
        @AttributeOverride(name = "modificationDate", column = @Column(name = "modification_date"))}
)
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

    @ManyToOne(cascade = CascadeType.REFRESH)
    @LastModifiedBy
    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    private AccountEntity modifiedBy;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @CreatedBy
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private AccountEntity createdBy;
}
