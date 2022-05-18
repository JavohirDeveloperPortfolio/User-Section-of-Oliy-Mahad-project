package uz.oliymahad.userservice.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserRegisterDto {

    private String phoneNumber;

    private String password;

    private Set<String> roles;
}
