package uz.oliymahad.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateDto {

    private String firstName;

    private String lastName;

    private String passport;

    private String email;

    private String gender;

    private Date birthDate;
}
