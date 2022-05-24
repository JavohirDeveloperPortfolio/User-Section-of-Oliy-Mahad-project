package uz.oliymahad.userservice.exception.handler;


import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import uz.oliymahad.userservice.exception.custom_ex_model.UserAlreadyRegisteredException;
import uz.oliymahad.userservice.security.jwt.advice.ErrorMessage;
import java.util.Date;


@RequiredArgsConstructor
@RestControllerAdvice
public class ApplicationExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(value = JwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handleTokenRefreshException(JwtException ex, WebRequest request) {
        logger.warn(ex.getMessage());
        return new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(value = UserAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleUserAlreadyRegisteredException(UserAlreadyRegisteredException ex, WebRequest request){
        logger.warn(ex.getMessage());
        return new ErrorMessage(
                HttpStatus.CONFLICT.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(value = PSQLException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handlePSQLException(PSQLException ex, WebRequest request){
       logger.warn(ex.getMessage());
       return new ErrorMessage(
               HttpStatus.CONFLICT.value(),
               new Date(),
               ex.getMessage(),
               request.getDescription(false)
       );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorMessage processUnmergeException(final MethodArgumentNotValidException ex, WebRequest request) {
        logger.warn(ex.getMessage());
        return new ErrorMessage(
                HttpStatus.NOT_ACCEPTABLE.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }
}
