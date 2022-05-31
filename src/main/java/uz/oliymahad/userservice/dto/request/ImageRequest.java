package uz.oliymahad.userservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageRequest {
    private String content;
    private String contentType;
    @Size(max = 5000)
    private long size;
}
