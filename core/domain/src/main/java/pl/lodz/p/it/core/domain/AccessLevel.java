package pl.lodz.p.it.core.domain;

/**
 * Class responsible for keeping a domain model of the access level object.
 */
public class AccessLevel extends BaseModel {

    private String level;

    private Account account;

    private Boolean active;

    private Account modifiedBy;

    private Account createdBy;
}
