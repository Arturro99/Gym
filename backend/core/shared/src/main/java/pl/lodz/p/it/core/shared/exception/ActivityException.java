package pl.lodz.p.it.core.shared.exception;

import pl.lodz.p.it.core.shared.exception.core.BaseException;
import pl.lodz.p.it.core.shared.exception.core.ConflictException;
import pl.lodz.p.it.core.shared.exception.core.NotFoundException;

/**
 * Class responsible for creating exceptions associated with activity object.
 */
public class ActivityException extends BaseException {

    private ActivityException(BaseException exception) {
        super(exception, exception.getErrorKey());
    }

    public static ActivityException activityNotFoundException() {
        return new ActivityException(NotFoundException
            .notFound("Required activity not found!", ErrorKey.ACTIVITY_NOT_FOUND_ERROR));
    }

    public static ActivityException existingActivityConflictException() {
        return new ActivityException(
            ConflictException.conflict("Activity already exists!",
                ErrorKey.ACTIVITY_CONFLICT_ERROR));
    }

    public static ActivityException activeActivityConflictException() {
        return new ActivityException(
            ConflictException.conflict("Activity is still active!",
                ErrorKey.ACTIVITY_CONFLICT_ERROR));
    }

    public static ActivityException inUseActivityConflictException() {
        return new ActivityException(
            ConflictException.conflict("Activity is being used!",
                ErrorKey.ACTIVITY_CONFLICT_ERROR));
    }

    public static ActivityException activityExpiredException() {
        return new ActivityException(
            ConflictException
                .conflict("Activity is already finished!", ErrorKey.ACTIVITY_CONFLICT_ERROR));
    }
}
