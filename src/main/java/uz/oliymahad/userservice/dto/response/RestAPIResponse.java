package uz.oliymahad.userservice.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RestAPIResponse {

    private String message;
    private boolean success;
    private HttpStatus status;
    private Object object;

    public RestAPIResponse(String message, boolean success, HttpStatus status) {
        this.message = message;
        this.success = success;
        this.status = status;
    }
}
