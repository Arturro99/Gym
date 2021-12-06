package pl.lodz.p.it.core.shared.exception.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.lodz.p.it.core.shared.exception.ErrorKey;

/**
 * Base class exception used by custom exceptions and providing general exceptions.
 */
@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {

    private final String errorKey;

    private BaseException(BaseException exception) {
        this(exception, exception.getErrorKey());
    }

    protected BaseException(String message, String errorKey) {
        super(message);
        this.errorKey = errorKey;
    }

    protected BaseException(Throwable cause, String errorKey) {
        super(cause);
        this.errorKey = errorKey;
    }

    public static BaseException notFoundException() {
        return new BaseException(NotFoundException
            .notFound("Required object not found!", ErrorKey.NOT_FOUND_ERROR));
    }

    public static BaseException conflictException() {
        return new BaseException(
            ConflictException.conflict("Object already exists!", ErrorKey.CONFLICT_ERROR));
    }
}
