package pl.lodz.p.it.core.shared.validation;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static pl.lodz.p.it.core.shared.validation.RegexPattern.TRAINING_PLAN_NUMBER_PATTERN;

/**
 * Annotation with custom validation for latitude.
 */
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@NotBlank
@Size(min = 6, max = 6, message = "Number needs have exactly 6 characters")
@Pattern(regexp = TRAINING_PLAN_NUMBER_PATTERN, message = "Invalid regex of the number")
public @interface TrainingPlanNumber {

    String message() default "Invalid number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
