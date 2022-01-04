package pl.lodz.p.it.core.shared.exception.core;

public class UnauthorizedException extends BaseException {

    private UnauthorizedException(String message, String errorKey) {
        super(message, errorKey);
    }

    public static UnauthorizedException unauthorized(String message, String errorKey) {
        return new UnauthorizedException(message, errorKey);
    }
}
