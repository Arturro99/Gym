package pl.lodz.p.it.core.shared.exception;

import pl.lodz.p.it.core.shared.exception.core.BaseException;
import pl.lodz.p.it.core.shared.exception.core.ConflictException;
import pl.lodz.p.it.core.shared.exception.core.NotFoundException;

/**
 * Class responsible for creating exceptions associated with diet object.
 */
public class DietException extends BaseException {

    private DietException(BaseException exception) {
        super(exception, exception.getErrorKey());
    }

    public static DietException dietNotFoundException() {
        return new DietException(NotFoundException
            .notFound("Required diet not found!", ErrorKey.DIET_NOT_FOUND_ERROR));
    }

    public static DietException dietConflictException() {
        return new DietException(
            ConflictException
                .conflict("Diet is being used!", ErrorKey.DIET_CONFLICT_ERROR));
    }
}
