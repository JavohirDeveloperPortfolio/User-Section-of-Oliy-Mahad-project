package uz.oliymahad.userservice.annotation.phone_num_constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PhoneNumberValidator.class})
public @interface PhoneNumber {
    public String message() default "INVALID PHONE NUMBER FORMAT";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
