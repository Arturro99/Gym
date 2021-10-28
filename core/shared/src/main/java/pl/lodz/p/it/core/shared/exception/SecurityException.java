package pl.lodz.p.it.core.shared.exception;

import pl.lodz.p.it.core.shared.exception.core.BaseException;
import pl.lodz.p.it.core.shared.exception.core.ExpiredException;

/**
 * Class responsible for creating exceptions associated with security.
 */
public class SecurityException extends BaseException {

    private SecurityException(BaseException exception) {
        super(exception, exception.getErrorKey());
    }

    public static SecurityException jwtExpiredException() {
        return new SecurityException(ExpiredException.expired("JWT token espired!", ErrorKey.JWT_EXPIRED));
    }
}
