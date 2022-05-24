package uz.oliymahad.userservice.annotation.phone_num_constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null &&
                s.length() == 13 &&
                s.matches("\\d+") &&
                s.indexOf("+998") == 0;
    }
}
