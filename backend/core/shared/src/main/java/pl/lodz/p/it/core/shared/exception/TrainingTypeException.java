package pl.lodz.p.it.core.shared.exception;

import pl.lodz.p.it.core.shared.exception.core.BaseException;
import pl.lodz.p.it.core.shared.exception.core.NotFoundException;

/**
 * Class responsible for creating exceptions associated with training plan object.
 */
public class TrainingTypeException extends BaseException {

    private TrainingTypeException(BaseException exception) {
        super(exception, exception.getErrorKey());
    }

    public static TrainingTypeException trainingTypeNotFoundException() {
        return new TrainingTypeException(NotFoundException
                .notFound("Required training type not found!", ErrorKey.TRAINING_TYPE_NOT_FOUND_ERROR));
    }
}
