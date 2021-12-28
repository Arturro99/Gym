package pl.lodz.p.it.core.shared.exception;

import pl.lodz.p.it.core.shared.exception.core.BaseException;
import pl.lodz.p.it.core.shared.exception.core.ConflictException;
import pl.lodz.p.it.core.shared.exception.core.NotFoundException;

/**
 * Class responsible for creating exceptions associated with training plan object.
 */
public class TrainingPlanException extends BaseException {

    private TrainingPlanException(BaseException exception) {
        super(exception, exception.getErrorKey());
    }

    public static TrainingPlanException trainingPlanNotFoundException() {
        return new TrainingPlanException(NotFoundException
            .notFound("Required training plan not found!", ErrorKey.TRAINING_PLAN_NOT_FOUND_ERROR));
    }

    public static TrainingPlanException existingTrainingPlanConflictException() {
        return new TrainingPlanException(
            ConflictException
                .conflict("Training plan already exists!", ErrorKey.TRAINING_PLAN_CONFLICT_ERROR));
    }

    public static TrainingPlanException inUseTrainingPlanConflictException() {
        return new TrainingPlanException(
            ConflictException
                .conflict("Training plan is being used!", ErrorKey.TRAINING_PLAN_CONFLICT_ERROR));
    }

    public static TrainingPlanException possessedTrainingPlanConflictException() {
        return new TrainingPlanException(
            ConflictException
                .conflict("User already possesses the training plan!",
                    ErrorKey.TRAINING_PLAN_CONFLICT_ERROR));
    }

    public static TrainingPlanException trainerTrainingPlanConflictException() {
        return new TrainingPlanException(
            ConflictException
                .conflict("The trainer runs provided training plan!",
                    ErrorKey.TRAINING_PLAN_CONFLICT_TRAINER_ERROR));
    }
}
