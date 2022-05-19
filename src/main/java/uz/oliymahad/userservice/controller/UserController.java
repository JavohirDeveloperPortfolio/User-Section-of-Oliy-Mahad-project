package uz.oliymahad.userservice.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.oliymahad.userservice.dto.UserRegisterDto;
import uz.oliymahad.userservice.dto.response.ApiResponse;
import uz.oliymahad.userservice.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public HttpEntity<?> registerUser(@RequestBody UserRegisterDto userRegisterDto){
        ApiResponse register = userService.register(userRegisterDto);
        return ResponseEntity.ok(register);
    }
}
