package pl.lodz.p.it.core.shared.exception;

import pl.lodz.p.it.core.shared.exception.core.BaseException;
import pl.lodz.p.it.core.shared.exception.core.NotFoundException;

/**
 * Class responsible for creating exceptions associated with training plan object.
 */
public class DietTypeException extends BaseException {

    private DietTypeException(BaseException exception) {
        super(exception, exception.getErrorKey());
    }

    public static DietTypeException dietTypeNotFoundException() {
        return new DietTypeException(NotFoundException
                .notFound("Required diet type not found!", ErrorKey.DIET_TYPE_NOT_FOUND_ERROR));
    }
}
