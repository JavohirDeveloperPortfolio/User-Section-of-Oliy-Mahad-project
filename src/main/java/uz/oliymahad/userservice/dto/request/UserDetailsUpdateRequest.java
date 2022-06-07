package uz.oliymahad.userservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDetailsUpdateRequest {

    private String firstName;

    private String lastName;

    private String passport;

    private String email;

    private String gender;

    private Date birthDate;
}
