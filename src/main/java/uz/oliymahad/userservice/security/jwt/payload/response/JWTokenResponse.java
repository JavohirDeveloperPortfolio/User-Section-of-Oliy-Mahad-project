package uz.oliymahad.userservice.security.jwt.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JWTokenResponse {
    private int statusCode;

    private String message;

    private String accessToken;

    private String refreshToken;
}
