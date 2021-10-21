package pl.lodz.p.it.repositoryhibernate.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "modification_date", nullable = false)
    private LocalDateTime modificationDate;
}
