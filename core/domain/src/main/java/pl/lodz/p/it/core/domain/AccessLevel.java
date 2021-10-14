package pl.lodz.p.it.core.domain;

import java.sql.Timestamp;

/**
 * Class responsible for keeping a domain model of the access level object.
 */
public class AccessLevel {

    private String level;

    private Account account;

    private Boolean active;

    private Timestamp creationDate;

    private Timestamp modificationDate;

    private Account modifiedBy;

    private Account createdBy;
}
