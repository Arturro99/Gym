package pl.lodz.p.it.core.shared.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class used for checking if the given CharSequence matches one of {@link pl.lodz.p.it.core.shared.constant.UrlAction} values.
 */
public class UrlActionValidator implements ConstraintValidator<UrlActions, CharSequence> {
    private List<String> acceptedValues;

    @Override
    public void initialize(UrlActions annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return acceptedValues.contains(value.toString());
    }
}