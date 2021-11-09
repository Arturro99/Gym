package pl.lodz.p.it.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import pl.lodz.p.it.core.shared.constant.Level;
import pl.lodz.p.it.core.shared.validation.AccessLevels;

import javax.validation.constraints.NotNull;

/**
 * Class responsible for keeping a domain model of the access level object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AccessLevel extends BaseModel {

    @AccessLevels(enumClass = Level.class)
    private String level;

    @NotNull
    private Account account;

    @NotNull
    private Boolean active;

    private Account modifiedBy;

    private Account createdBy;
}
