package pl.lodz.p.it.core.shared.exception.core;

/**
 * Base exception class used for creating exceptions associated with expiration.
 */
public class ExpiredException extends BaseException {

    private ExpiredException(String message, String errorKey) {
        super(message, errorKey);
    }

    public static ExpiredException expired(String message, String errorKey) {
        return new ExpiredException(message, errorKey);
    }
}
