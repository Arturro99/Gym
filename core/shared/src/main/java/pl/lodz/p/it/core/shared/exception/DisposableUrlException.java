package pl.lodz.p.it.core.shared.exception;

import pl.lodz.p.it.core.shared.exception.core.BaseException;
import pl.lodz.p.it.core.shared.exception.core.ConflictException;
import pl.lodz.p.it.core.shared.exception.core.NotFoundException;

/**
 * Class responsible for creating exceptions associated with disposable url object.
 */
public class DisposableUrlException extends BaseException {

    private DisposableUrlException(BaseException exception) {
        super(exception, exception.getErrorKey());
    }

    public static DisposableUrlException urlNotFoundException() {
        return new DisposableUrlException(NotFoundException
            .notFound("Required url not found!", ErrorKey.URL_NOT_FOUND_ERROR));
    }

    public static DisposableUrlException urlConflictException() {
        return new DisposableUrlException(
            ConflictException
                .conflict("Url already exists!", ErrorKey.URL_CONFLICT_ERROR));
    }
}
