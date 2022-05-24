package uz.oliymahad.userservice.exception.custom_ex_model;

public class UserNotFoundException extends UserAuthenticationException{

    public UserNotFoundException(String message) {
        super(message);
    }
}
