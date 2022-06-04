package uz.oliymahad.userservice.annotation.role_constraint;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Target(value = {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RoleValidator.class)
public @interface Role {
    public String message() default "Invalid role format";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
