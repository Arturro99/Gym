package pl.lodz.p.it.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;


/**
 * Class responsible for keeping a domain model of the disposable url object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DisposableUrl extends BaseModel {

    private String url;

    private Account account;

    private String actionType;

    private String newEmail;

    private OffsetDateTime expireDate;

    private Account modifiedBy;

    private Account createdBy;
}
