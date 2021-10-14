package pl.lodz.p.it.core.domain;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * Class responsible for keeping a domain model of the disposable url object.
 */
public class DisposableUrl {

    private final Timestamp creationDate = Timestamp.from(Instant.now());

    private String url;

    private Account account;

    private String actionType;

    private String newEmail;

    private Timestamp expireDate;

    private Timestamp modificationDate;

    private Account modifiedBy;

    private Account createdBy;
}
