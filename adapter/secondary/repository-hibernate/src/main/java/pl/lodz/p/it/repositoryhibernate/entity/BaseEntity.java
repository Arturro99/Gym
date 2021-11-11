package pl.lodz.p.it.repositoryhibernate.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.OffsetDateTime;

/**
 * Class responsible for keeping a base entity model.
 */
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Data
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String businessId;

    @NotNull
    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "creation_date", nullable = false, updatable = false)
    @Past
    private OffsetDateTime creationDate;

    @Column(name = "modification_date")
    @Past
    private OffsetDateTime modificationDate;

    @PrePersist
    void prePersist() {
        creationDate = OffsetDateTime.now();
        modificationDate = creationDate;
    }

    @PreUpdate
    void preUpdate() {
        modificationDate = OffsetDateTime.now();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getBusinessId() != null ? this.getBusinessId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object.getClass() != this.getClass()) {
            return false;
        }
        BaseEntity other = (BaseEntity) object;
        return (this.getBusinessId() != null || other.getBusinessId() == null) &&
                (this.getBusinessId() == null || this.getBusinessId().equals(other.getBusinessId()));
    }
}
