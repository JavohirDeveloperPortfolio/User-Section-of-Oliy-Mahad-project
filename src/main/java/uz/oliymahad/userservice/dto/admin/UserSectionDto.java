package uz.oliymahad.userservice.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSectionDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phoneNumber;
    private String gender;
}
