package pl.lodz.p.it.core.shared.exception;

/**
 * Class responsible for keeping error keys.
 */
public final class ErrorKey {

    public static final String NOT_FOUND_ERROR = "error.general.notFound";
    public static final String CONFLICT_ERROR = "error.general.conflict";
    public static final String CONSTRAINT_VIOLATION = "error.general.constraintViolation";
    public static final String INTERNAL_SERVER_ERROR = "error.general.internalServerError";
    public static final String BAD_CREDENTIALS = "error.general.badCredentials";
    public static final String OPTIMISTIC_LOCK = "error.general.optimisticLock";

    public static final String ACCESS_LEVEL_NOT_FOUND_ERROR = "error.accessLevel.notFound";
    public static final String ACCESS_LEVEL_BAD_REQUEST_ERROR = "error.accessLevel.badRequest";

    public static final String ACCOUNT_NOT_FOUND_ERROR = "error.account.notFound";
    public static final String ACCOUNT_CONFLICT_ERROR = "error.account.conflict";

    public static final String ACTIVITY_NOT_FOUND_ERROR = "error.activity.notFound";
    public static final String ACTIVITY_CONFLICT_ERROR = "error.activity.conflict";
    public static final String ACTIVITY_CONFLICT_INACTIVE_ERROR = "error.activity.inactive.conflict";
    public static final String ACTIVITY_CONFLICT_IN_USE_ERROR = "error.activity.inUse.conflict";

    public static final String BOOKING_NOT_FOUND_ERROR = "error.booking.notFound";
    public static final String BOOKING_CONFLICT_ERROR = "error.booking.conflict";
    public static final String BOOKING_CONFLICT_CANCELLATION_ERROR = "error.booking.cancellation.conflict";
    public static final String BOOKING_CONFLICT_COMPLETION_ERROR = "error.booking.completion.conflict";
    public static final String BOOKING_CONFLICT_CLIENT_TRAINER_ERROR = "error.booking.clientTrainer.conflict";
    public static final String BOOKING_CONFLICT_ACTIVITY_INACTIVE_ERROR = "error.booking.activityInactive.conflict";

    public static final String DIET_NOT_FOUND_ERROR = "error.diet.notFound";
    public static final String DIET_CONFLICT_ERROR = "error.diet.conflict";

    public static final String DIET_TYPE_NOT_FOUND_ERROR = "error.dietType.notFound";

    public static final String URL_NOT_FOUND_ERROR = "error.url.notFound";
    public static final String URL_CONFLICT_ERROR = "error.url.conflict";
    public static final String URL_EXPIRED_ERROR = "error.url.gone";

    public static final String TRAINING_PLAN_NOT_FOUND_ERROR = "error.trainingPlan.notFound";
    public static final String TRAINING_PLAN_CONFLICT_ERROR = "error.trainingPlan.conflict";
    public static final String TRAINING_PLAN_CONFLICT_TRAINER_ERROR = "error.trainingPlan.trainer.conflict";

    public static final String TRAINING_TYPE_NOT_FOUND_ERROR = "error.trainingType.notFound";

    public static final String JWT_EXPIRED = "error.jwt.expired";
    public static final String ACCOUNT_INACTIVE = "error.account.inactive";

}
