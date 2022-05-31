package uz.oliymahad.userservice.security.jwt.exception;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class JWTokenException extends JwtException {

  public JWTokenException(String token, String message) {
    super(String.format("Failed for [%s]: %s", token, message));
  }
}
