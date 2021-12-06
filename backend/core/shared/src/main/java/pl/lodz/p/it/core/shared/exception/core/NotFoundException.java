package pl.lodz.p.it.core.shared.exception.core;

public class NotFoundException extends BaseException {

    private NotFoundException(String message, String errorKey) {
        super(message, errorKey);
    }

    public static NotFoundException notFound(String message, String errorKey) {
        return new NotFoundException(message, errorKey);
    }
}
