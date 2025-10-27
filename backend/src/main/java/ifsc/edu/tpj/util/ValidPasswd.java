package ifsc.edu.tpj.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPasswd {
    String message() default "Passwd needs: 6+ chars, uppercase, lowercase, number, and special char";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
