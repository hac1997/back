package ifsc.edu.tpj.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TagValidator implements ConstraintValidator<ValidTag, String> {

    private static final String TAG_PATTERN = "^[a-zA-Z0-9_-]{3,30}$";

    @Override
    public void initialize(ValidTag constraintAnnotation) {
    }

    @Override
    public boolean isValid(String tag, ConstraintValidatorContext context) {
        if (tag == null) {
            return false;
        }
        String trimmed = tag.trim();
        if (trimmed.isEmpty()) {
            return false;
        }
        return trimmed.matches(TAG_PATTERN);
    }
}
