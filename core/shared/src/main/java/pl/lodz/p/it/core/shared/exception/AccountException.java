package pl.lodz.p.it.core.shared.exception;

import pl.lodz.p.it.core.shared.exception.core.BaseException;
import pl.lodz.p.it.core.shared.exception.core.ConflictException;
import pl.lodz.p.it.core.shared.exception.core.NotFoundException;

/**
 * Class responsible for creating exceptions associated with account object.
 */
public class AccountException extends BaseException {

    private AccountException(BaseException exception) {
        super(exception, exception.getErrorKey());
    }

    public static AccountException accountNotFoundException() {
        return new AccountException(NotFoundException
            .notFound("Required account not found!", ErrorKey.ACCOUNT_NOT_FOUND_ERROR));
    }

    public static AccountException accountConflictException() {
        return new AccountException(
            ConflictException.conflict("Account already exists!", ErrorKey.ACCOUNT_CONFLICT_ERROR));
    }
}
