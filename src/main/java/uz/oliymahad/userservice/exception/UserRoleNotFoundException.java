package uz.oliymahad.userservice.exception;

public class UserRoleNotFoundException extends RuntimeException{
    public UserRoleNotFoundException(String message) {
        super(message);
    }
}
