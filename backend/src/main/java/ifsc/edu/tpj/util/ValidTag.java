package ifsc.edu.tpj.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TagValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTag {
    String message() default "Tag needs: 3-30 chars, not blank and have no special characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
