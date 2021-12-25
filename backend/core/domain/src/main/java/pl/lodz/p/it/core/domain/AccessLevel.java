package pl.lodz.p.it.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.lodz.p.it.core.shared.constant.Level;
import pl.lodz.p.it.core.shared.validation.AccessLevels;

/**
 * Class responsible for keeping a domain model of the access level object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AccessLevel extends BaseModel {

    @AccessLevels(enumClass = Level.class)
    private String level;

    private String account;

    private Boolean active;

    private String modifiedBy;

    private String createdBy;
}
