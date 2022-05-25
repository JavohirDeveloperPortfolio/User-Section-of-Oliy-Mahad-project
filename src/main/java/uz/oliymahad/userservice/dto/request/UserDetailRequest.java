package uz.oliymahad.userservice.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class UserDetailRequest {


    private long userId;

    private String firstName;

    private String lastName;

    private String passport;

    private String email;

    private Date birthDate;
}
