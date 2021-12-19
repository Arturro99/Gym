package pl.lodz.p.it.core.shared.exception.core;

public class ForbiddenException extends BaseException {

    private ForbiddenException(String message, String errorKey) {
        super(message, errorKey);
    }

    public static ForbiddenException forbidden(String message, String errorKey) {
        return new ForbiddenException(message, errorKey);
    }
}
