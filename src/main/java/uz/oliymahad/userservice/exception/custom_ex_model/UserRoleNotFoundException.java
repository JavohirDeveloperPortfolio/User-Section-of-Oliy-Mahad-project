package uz.oliymahad.userservice.exception.custom_ex_model;

public class UserRoleNotFoundException extends RuntimeException{
    public UserRoleNotFoundException(String message) {
        super(message);
    }
}
