package uz.oliymahad.userservice.dto.request;

import lombok.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsRegisterRequest {
    @NotBlank
    private Long userId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String middleName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String gender;

    @NotBlank
    private String passport;

    @NotBlank
    private Date birthDate;
}
