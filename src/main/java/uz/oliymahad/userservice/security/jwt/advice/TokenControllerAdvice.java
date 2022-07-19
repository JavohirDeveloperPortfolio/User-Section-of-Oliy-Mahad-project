package uz.oliymahad.userservice.security.jwt.advice;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import uz.oliymahad.userservice.dto.response.ErrorMessageResponse;
import uz.oliymahad.userservice.security.jwt.exception.TokenRefreshException;

import java.util.Date;

@RestControllerAdvice
public class TokenControllerAdvice {

  @ExceptionHandler(value = JwtException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ErrorMessageResponse handleTokenRefreshException(JwtException ex, WebRequest request) {
    System.out.println(ex.getMessage());
    return new ErrorMessageResponse(
        HttpStatus.FORBIDDEN.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false));
  }
}
