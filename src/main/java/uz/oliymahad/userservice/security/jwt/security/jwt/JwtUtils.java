package uz.oliymahad.userservice.security.jwt.security.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uz.oliymahad.userservice.model.entity.UserEntity;

import java.util.Date;

@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${jwtSecret}")
  private String jwtSecret;

  @Value("${jwtExpirationMs}")
  private int accessTokenExpiration;

  @Value("${jwtRefreshExpirationMs}")
  private long refreshTokenExpiration;

  public String[] generateJwtTokens(UserEntity userPrincipal) {
    return new String[]{
            generateAccessTokenFromPhoneNumber(userPrincipal.getPhoneNumber()),
            generateRefreshTokenFromPhoneNumber(userPrincipal.getPhoneNumber())
    };
  }

  public String generateAccessTokenFromPhoneNumber(String phoneNumber) {
    return Jwts.builder().setSubject(phoneNumber).setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + accessTokenExpiration)).signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String generateRefreshTokenFromPhoneNumber(String phoneNumber){
    return Jwts.builder().setSubject(phoneNumber).setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + refreshTokenExpiration)).signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
  }

  public String getPhoneNumberFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }

}
