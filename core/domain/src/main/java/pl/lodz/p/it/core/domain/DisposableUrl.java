package pl.lodz.p.it.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Email;
import pl.lodz.p.it.core.shared.constant.UrlAction;
import pl.lodz.p.it.core.shared.validation.UrlActions;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;


/**
 * Class responsible for keeping a domain model of the disposable url object.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DisposableUrl extends BaseModel {

    @Size(min = 32, max = 32)
    private String url;

    private Account account;

    @NotBlank
    @UrlActions(enumClass = UrlAction.class)
    private String actionType;

    @Email(message = "Email is not valid")
    private String newEmail;

    @Future
    private OffsetDateTime expireDate;

    private Account modifiedBy;

    private Account createdBy;
}
