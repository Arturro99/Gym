package pl.lodz.p.it.gymbackend.advice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.GONE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import javax.validation.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.lodz.p.it.core.shared.exception.ErrorKey;
import pl.lodz.p.it.core.shared.exception.core.BadRequestException;
import pl.lodz.p.it.core.shared.exception.core.ConflictException;
import pl.lodz.p.it.core.shared.exception.core.ExpiredException;
import pl.lodz.p.it.core.shared.exception.core.NotFoundException;
import pl.lodz.p.it.core.shared.exception.core.UnauthorizedException;
import pl.lodz.p.it.restapi.dto.ErrorResponse;

/**
 * Class responsible for handling application exceptions.
 */
@RestControllerAdvice
public class RestExceptionAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse notFound(NotFoundException e) {
        return ErrorResponse.error(e.getErrorKey(), e.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(CONFLICT)
    public ErrorResponse conflict(ConflictException e) {
        return ErrorResponse.error(e.getErrorKey(), e.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ErrorResponse unauthorized(UnauthorizedException e) {
        return ErrorResponse.error(e.getErrorKey(), e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse badRequest(BadRequestException e) {
        return ErrorResponse.error(e.getErrorKey(), e.getMessage());
    }

    @ExceptionHandler(ExpiredException.class)
    @ResponseStatus(GONE)
    public ErrorResponse expiredException(ExpiredException e) {
        return ErrorResponse.error(e.getErrorKey(), e.getMessage());
    }

    @ExceptionHandler(PSQLException.class)
    @ResponseStatus(CONFLICT)
    public ErrorResponse conflict(PSQLException e) {
        String errorKey = ErrorKey.CONFLICT_ERROR;
        String message = "Conflict occurred!";

        if (e.getCause().getMessage().contains("training_plan_number_key")) {
            errorKey = ErrorKey.TRAINING_PLAN_CONFLICT_ERROR;
            message = "Training plan already exists!";
        } else if (e.getCause().getMessage().contains("account_email_key") || e.getCause()
            .getMessage().contains("account_login_key")) {
            errorKey = ErrorKey.ACCOUNT_CONFLICT_ERROR;
            message = "User with provided email or login exists!";
        } else if (e.getCause().getMessage().contains("diet_number_key")) {
            errorKey = ErrorKey.DIET_CONFLICT_ERROR;
            message = "Diet already exists!";
        } else if (e.getCause().getMessage().contains("activity_number_key")) {
            errorKey = ErrorKey.ACTIVITY_CONFLICT_ERROR;
            message = "Activity already exists!";
        } else if (e.getCause().getMessage().contains("booking_account_activity_key")) {
            errorKey = ErrorKey.BOOKING_CONFLICT_ERROR;
            message = "Such booking already exists!";
        }

        return ErrorResponse.error(errorKey, message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse constraintViolation(ConstraintViolationException e) {
        return ErrorResponse.error(ErrorKey.CONSTRAINT_VIOLATION, e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse nullPointerException(NullPointerException e) {
        return ErrorResponse.error(ErrorKey.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
