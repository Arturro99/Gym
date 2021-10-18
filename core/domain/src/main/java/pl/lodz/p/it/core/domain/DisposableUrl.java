package pl.lodz.p.it.core.domain;

import java.sql.Timestamp;

/**
 * Class responsible for keeping a domain model of the disposable url object.
 */
public class DisposableUrl extends BaseModel {

    private String url;

    private Account account;

    private String actionType;

    private String newEmail;

    private Timestamp expireDate;

    private Account modifiedBy;

    private Account createdBy;
}
