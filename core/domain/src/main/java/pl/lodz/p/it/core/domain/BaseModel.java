package pl.lodz.p.it.core.domain;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Class responsible for keeping a base domain model.
 */
@NoArgsConstructor
public abstract class BaseModel {

    private Long version;

    private LocalDateTime creationDate;

    private LocalDateTime modificationDate;
}
