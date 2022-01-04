package pl.lodz.p.it.core.shared.exception;

import pl.lodz.p.it.core.shared.exception.core.BaseException;
import pl.lodz.p.it.core.shared.exception.core.ConflictException;
import pl.lodz.p.it.core.shared.exception.core.UnauthorizedException;

/**
 * Class responsible for creating exceptions associated with account object.
 */
public class BadCredentialsException extends BaseException {

    private BadCredentialsException(BaseException exception) {
        super(exception, exception.getErrorKey());
    }

    public static BadCredentialsException badCredentials() {
        return new BadCredentialsException(
            UnauthorizedException.unauthorized("Bad credentials", ErrorKey.BAD_CREDENTIALS));
    }
}
