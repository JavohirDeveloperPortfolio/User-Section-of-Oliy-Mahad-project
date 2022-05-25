package uz.oliymahad.userservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.oliymahad.userservice.annotation.phone_num_constraint.PhoneNumber;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    @PhoneNumber
    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String password;

}
