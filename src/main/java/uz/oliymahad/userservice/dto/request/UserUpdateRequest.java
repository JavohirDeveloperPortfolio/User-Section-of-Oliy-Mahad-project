package uz.oliymahad.userservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.oliymahad.userservice.annotation.phone_num_constraint.PhoneNumber;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateRequest {

    @PhoneNumber
    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String passport;

    private String email;

    private String gender;

    private Date birthDate;
}
