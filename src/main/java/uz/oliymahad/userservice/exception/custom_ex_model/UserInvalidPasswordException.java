package uz.oliymahad.userservice.exception.custom_ex_model;

public class UserInvalidPasswordException extends UserAuthenticationException {
    public UserInvalidPasswordException(String message) {
        super(message);
    }
}
