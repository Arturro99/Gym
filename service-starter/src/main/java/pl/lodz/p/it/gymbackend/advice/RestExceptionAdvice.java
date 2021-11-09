package pl.lodz.p.it.gymbackend.advice;

import org.postgresql.util.PSQLException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.lodz.p.it.core.shared.exception.ErrorKey;
import pl.lodz.p.it.core.shared.exception.core.BadRequestException;
import pl.lodz.p.it.core.shared.exception.core.ConflictException;
import pl.lodz.p.it.core.shared.exception.core.NotFoundException;
import pl.lodz.p.it.restapi.dto.ErrorResponse;

import javax.validation.ConstraintViolationException;

import static org.springframework.http.HttpStatus.*;

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

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse badRequest(BadRequestException e) {
        return ErrorResponse.error(e.getErrorKey(), e.getMessage());
    }

    @ExceptionHandler(PSQLException.class)
    @ResponseStatus(CONFLICT)
    public ErrorResponse conflict(PSQLException e) {
        String errorKey = ErrorKey.CONFLICT_ERROR;
        String message = "Conflict occurred!";

        if (e.getMessage().contains("training_plan_number_key")) {
            errorKey = ErrorKey.TRAINING_PLAN_CONFLICT_ERROR;
            message = "Training plan already exists!";
        } else if (e.getMessage().contains("account_email_key") || e.getMessage().contains("account_login_key")) {
            errorKey = ErrorKey.ACCOUNT_CONFLICT_ERROR;
            message = "User with provided email or login exists!";
        } else if (e.getMessage().contains("diet_number_key")) {
            errorKey = ErrorKey.DIET_CONFLICT_ERROR;
            message = "Diet already exists!";
        }

        return ErrorResponse.error(errorKey, message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse constraintViolation(ConstraintViolationException e) {
        return ErrorResponse.error(ErrorKey.CONSTRAINT_VIOLATION, e.getMessage());
    }
}