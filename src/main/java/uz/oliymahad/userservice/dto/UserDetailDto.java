package uz.oliymahad.userservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDetailDto {

    private long userId;

    private String firstName;

    private String lastName;

    private String passport;

    private String email;

    private Date birthDate;
}
