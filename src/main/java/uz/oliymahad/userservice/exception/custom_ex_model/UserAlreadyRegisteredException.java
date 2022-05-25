package uz.oliymahad.userservice.exception.custom_ex_model;

import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;

public class UserAlreadyRegisteredException extends Exception{
    public UserAlreadyRegisteredException(String message) {
        super(message);
    }
}

