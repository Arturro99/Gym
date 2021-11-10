package pl.lodz.p.it.repositoryhibernate.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
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
}
