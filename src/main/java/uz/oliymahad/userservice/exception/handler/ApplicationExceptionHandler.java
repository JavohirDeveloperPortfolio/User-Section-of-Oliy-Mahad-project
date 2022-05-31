package uz.oliymahad.userservice.exception.handler;


import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import uz.oliymahad.userservice.exception.custom_ex_model.UserAlreadyRegisteredException;
import uz.oliymahad.userservice.exception.custom_ex_model.UserAuthenticationException;
import uz.oliymahad.userservice.dto.response.ErrorMessageResponse;
import java.util.Date;


@RequiredArgsConstructor
@RestControllerAdvice
public class ApplicationExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(value = {IllegalArgumentException.class,ClassCastException.class, IllegalStateException.class,
            InvalidDataAccessApiUsageException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageResponse handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request){
        logger.warn(ex.getMessage());
        return new ErrorMessageResponse(
                HttpStatus.NOT_IMPLEMENTED.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
    }
    @ExceptionHandler(value = JwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessageResponse handleTokenRefreshException(JwtException ex, WebRequest request) {
        logger.warn(ex.getMessage());
        return new ErrorMessageResponse(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(value = UserAuthenticationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessageResponse handleUserAuthenticationException(UserAuthenticationException ex, WebRequest request){
        logger.warn(ex.getMessage());
        return new ErrorMessageResponse(
                HttpStatus.CONFLICT.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(value = UserAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessageResponse handleUserAlreadyRegisteredException(UserAlreadyRegisteredException ex, WebRequest request){
        logger.warn(ex.getMessage());
        return new ErrorMessageResponse(
                HttpStatus.CONFLICT.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }
    @ExceptionHandler(value = PSQLException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessageResponse handlePSQLException(PSQLException ex, WebRequest request){
       logger.warn(ex.getMessage());
       return new ErrorMessageResponse(
               HttpStatus.CONFLICT.value(),
               new Date(),
               ex.getMessage(),
               request.getDescription(false)
       );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorMessageResponse processUnmergeException(final MethodArgumentNotValidException ex, WebRequest request) {
        logger.warn(ex.getMessage());
        return new ErrorMessageResponse(
                HttpStatus.NOT_ACCEPTABLE.value(),
                new Date(),
                ex.getFieldError().getDefaultMessage(),
                request.getDescription(false)
        );
    }
}
