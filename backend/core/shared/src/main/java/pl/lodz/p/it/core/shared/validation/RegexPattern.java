package pl.lodz.p.it.core.shared.validation;

/**
 * Class used for keeping different bean validation patterns.
 */
public final class RegexPattern {

    public static final String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$";

    public static final String PHONE_NUMBER_PATTERN = "^[0-9]{9}$";

    public static final String ACTIVITY_NUMBER_PATTERN = "^ACT[0-9]{3}$";

    public static final String DIET_NUMBER_PATTERN = "^DIE[0-9]{3}$";

    public static final String TRAINING_PLAN_NUMBER_PATTERN = "^TRA[0-9]{3}$";

    public static final String BOOKING_NUMBER_PATTERN = "^BOO[0-9]{3}$";
}
