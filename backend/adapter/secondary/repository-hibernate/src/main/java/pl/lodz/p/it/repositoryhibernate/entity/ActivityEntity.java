package pl.lodz.p.it.repositoryhibernate.entity;

import java.time.OffsetDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Class responsible for keeping an entity model of the activity object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "activity")
@AttributeOverrides(
    {@AttributeOverride(name = "businessId", column = @Column(name = "number", nullable = false, updatable = false, unique = true)),
        @AttributeOverride(name = "creationDate", column = @Column(name = "creation_date", nullable = false)),
        @AttributeOverride(name = "modificationDate", column = @Column(name = "modification_date"))}
)
@EntityListeners(AuditingEntityListener.class)
public class ActivityEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    @Column(name = "start_date", nullable = false)
    @NotNull
    @Future
    private OffsetDateTime startDate;

    @Column(name = "duration", nullable = false)
    @Range(min = 1)
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "trainer", nullable = false, referencedColumnName = "id")
    @NotNull
    private AccountEntity trainer;

    @Column(name = "active", nullable = false)
    @NotNull
    private Boolean active = true;

    @Column(name = "capacity", nullable = false)
    @NotNull
    private Integer capacity;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @LastModifiedBy
    @JoinColumn(name = "modified_by", referencedColumnName = "id")
    private AccountEntity modifiedBy;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @CreatedBy
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private AccountEntity createdBy;
}
