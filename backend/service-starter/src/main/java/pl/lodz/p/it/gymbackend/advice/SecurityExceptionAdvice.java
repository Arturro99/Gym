package pl.lodz.p.it.gymbackend.advice;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.lodz.p.it.core.shared.exception.SecurityException;
import pl.lodz.p.it.restapi.dto.ErrorResponse;

/**
 * Class responsible for handling security exceptions.
 */
@RestControllerAdvice
public class SecurityExceptionAdvice {

    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(FORBIDDEN)
    public ErrorResponse forbidden(SecurityException e) {
        return ErrorResponse.error(e.getErrorKey(), e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ErrorResponse badCredentials(BadCredentialsException e) {
        return ErrorResponse.error(e.getLocalizedMessage(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse unknown(Exception e) {
        return ErrorResponse.error(e.getLocalizedMessage(), e.getMessage());
    }
}
