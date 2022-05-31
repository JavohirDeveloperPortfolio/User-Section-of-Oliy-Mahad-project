package uz.oliymahad.userservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import uz.oliymahad.userservice.annotation.phone_num_constraint.PhoneNumber;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    private String phoneNumber;

    private String email;

    private String password;

    private ImageRequest image;
}
