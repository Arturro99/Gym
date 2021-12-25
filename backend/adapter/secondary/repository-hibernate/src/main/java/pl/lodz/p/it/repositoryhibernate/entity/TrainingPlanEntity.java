package pl.lodz.p.it.repositoryhibernate.entity;

import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Class responsible for keeping an entity model of the training plan object.
 */
@Getter
@Setter
@Entity
@Table(name = "training_plan")
@AttributeOverrides(
    {@AttributeOverride(name = "businessId", column = @Column(name = "number", nullable = false, updatable = false, unique = true)),
        @AttributeOverride(name = "creationDate", column = @Column(name = "creation_date", nullable = false)),
        @AttributeOverride(name = "modificationDate", column = @Column(name = "modification_date"))}
)
@EntityListeners(AuditingEntityListener.class)
public class TrainingPlanEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(name = "training_type", nullable = false, referencedColumnName = "id")
    @NotNull
    private TrainingTypeEntity trainingType;

    @Column(name = "personal_trainings_number", nullable = false)
    @Range(min = 0)
    private Integer personalTrainingsNumber;

    @ManyToOne
    @JoinColumn(name = "trainer", nullable = false, referencedColumnName = "id")
    @NotNull
    private AccountEntity trainer;

    @Column(name = "price", nullable = false)
    @Range(min = 1)
    private Double price;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "trainingPlans")
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
