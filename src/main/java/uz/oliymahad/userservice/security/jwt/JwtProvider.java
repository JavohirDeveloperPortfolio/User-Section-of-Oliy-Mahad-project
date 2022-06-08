package uz.oliymahad.userservice.security.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uz.oliymahad.userservice.model.entity.UserEntity;
import uz.oliymahad.userservice.security.oauth2.UserPrincipal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {
  private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

  @Value("${jwt.secret.key.access}")
  private String jwtAccessSecret;

  @Value("${jwt.secret.key.refresh}")
  private String jwtRefreshSecret;
  @Value("${jwt.secret.expiration.access}")
  private int accessTokenExpiration;

  @Value("${jwt.secret.expiration.refresh}")
  private long refreshTokenExpiration;

  public String[] generateJwtTokens(UserEntity userPrincipal) {
    return new String[]{
            generateAccessToken(userPrincipal),
            generateRefreshToken(userPrincipal)
    };
  }

  public String generateAccessToken(UserEntity user) {
    String subj = user.getPhoneNumber() == null ? user.getEmail() : user.getPhoneNumber();
    return "Bearer " +  Jwts.builder().setSubject(subj).claim("role",user.getRoles()).setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + accessTokenExpiration)).signWith(SignatureAlgorithm.HS512, jwtAccessSecret)
            .compact();
  }

  public String generateRefreshToken(UserEntity user){
    String subj = user.getPhoneNumber() == null ? user.getEmail() : user.getPhoneNumber();
    return "Bearer " + Jwts.builder().setSubject(subj).claim("role",user.getRoles()).setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + refreshTokenExpiration)).signWith(SignatureAlgorithm.HS512, jwtRefreshSecret)
            .compact();
  }


  public String generateAccessToken(UserPrincipal user) {
    String subj = user.getEmail();
    return "Bearer " + Jwts.builder().setSubject(subj).claim("role",user.getAuthorities()).setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + accessTokenExpiration)).signWith(SignatureAlgorithm.HS512, jwtAccessSecret)
            .compact();
  }

  public String generateRefreshToken(UserPrincipal user){
    String subj = user.getEmail();
    return "Bearer " + Jwts.builder().setSubject(subj).claim("role",user.getAuthorities()).setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + refreshTokenExpiration)).signWith(SignatureAlgorithm.HS512, jwtRefreshSecret)
            .compact();
  }


  public Jws<Claims> validateJwtAccessToken(String authToken) {
    try {
      return Jwts.parser().setSigningKey(jwtAccessSecret).parseClaimsJws(authToken);
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

    return null;
  }

  public Jws<Claims> validateJwtRefreshToken(String authToken) {
    try {
      return Jwts.parser().setSigningKey(jwtRefreshSecret).parseClaimsJws(authToken);
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

    return null;
  }

}
