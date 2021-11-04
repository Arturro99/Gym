package pl.lodz.p.it.core.shared.exception.core;

public class BadRequestException extends BaseException {

    private BadRequestException(String message, String errorKey) {
        super(message, errorKey);
    }

    public static BadRequestException badRequest(String message, String errorKey) {
        return new BadRequestException(message, errorKey);
    }
}
