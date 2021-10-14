package pl.lodz.p.it.core.shared.exception.core;

public class ConflictException extends BaseException {

    private ConflictException(String message, String errorKey) {
        super(message, errorKey);
    }

    public static ConflictException conflict(String message, String errorKey) {
        return new ConflictException(message, errorKey);
    }
}
