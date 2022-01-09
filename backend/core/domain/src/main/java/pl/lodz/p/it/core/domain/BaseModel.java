package pl.lodz.p.it.core.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import lombok.experimental.SuperBuilder;

/**
 * Class responsible for keeping a base domain model.
 */
@NoArgsConstructor
@SuperBuilder
@Data
public abstract class BaseModel {

    private Long version;

    private OffsetDateTime creationDate;

    private OffsetDateTime modificationDate;
}
