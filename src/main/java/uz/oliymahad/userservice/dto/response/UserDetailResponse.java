package uz.oliymahad.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDetailResponse {

    private long id;
    private long userId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String gender;
    private String passport;
}
