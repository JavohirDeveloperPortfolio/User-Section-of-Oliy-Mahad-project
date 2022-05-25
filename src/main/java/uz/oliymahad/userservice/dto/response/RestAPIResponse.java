package uz.oliymahad.userservice.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties
public class RestAPIResponse {

    private String message;
    private boolean success;
    private HttpStatus statusCode;

    private Object data;

    public RestAPIResponse(String message, boolean success, HttpStatus statusCode) {
        this.message = message;
        this.success = success;
        this.statusCode = statusCode;
    }
}
