package pl.lodz.p.it.core.shared.exception;

import pl.lodz.p.it.core.shared.exception.core.BadRequestException;
import pl.lodz.p.it.core.shared.exception.core.BaseException;
import pl.lodz.p.it.core.shared.exception.core.NotFoundException;

/**
 * Class responsible for creating exceptions associated with access level object.
 */
public class AccessLevelException extends BaseException {

    private AccessLevelException(BaseException exception) {
        super(exception, exception.getErrorKey());
    }

    public static AccessLevelException accessLevelNotFoundException() {
        return new AccessLevelException(NotFoundException
            .notFound("Required access level not found!", ErrorKey.ACCESS_LEVEL_NOT_FOUND_ERROR));
    }

    public static AccessLevelException illegalAccessLevel() {
        return new AccessLevelException(BadRequestException.
                badRequest("Inappropriate access level!", ErrorKey.ACCESS_LEVEL_BAD_REQUEST_ERROR));
    }
}
