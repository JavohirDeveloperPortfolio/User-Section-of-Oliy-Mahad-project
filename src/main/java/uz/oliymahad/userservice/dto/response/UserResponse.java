package uz.oliymahad.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponse {

    private long id;
    private String username;
    private String phoneNumber;
    private String email;
    private long userRegisterDetailsId;
    private String imageUrl;
}
