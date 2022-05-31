package uz.oliymahad.userservice.exception.custom_ex_model;

public abstract class UserAuthenticationException extends RuntimeException{
    public UserAuthenticationException(String message) {
        super(message);
    }
}
