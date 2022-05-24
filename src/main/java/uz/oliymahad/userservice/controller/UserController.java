package uz.oliymahad.userservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.oliymahad.userservice.dto.UserRegisterDto;
import uz.oliymahad.userservice.dto.UserUpdateDto;
import uz.oliymahad.userservice.dto.response.ApiResponse;
import uz.oliymahad.userservice.model.entity.UserEntity;
import uz.oliymahad.userservice.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public HttpEntity<?> register(@RequestBody UserRegisterDto registerDto){
        ApiResponse apiResponse = userService.register(registerDto);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/getPage")
    public HttpEntity<?> getPage(@RequestParam int page) {
        return ResponseEntity.ok(userService.getPage(page));
    }


    @PutMapping("/edit")
    public HttpEntity<?> editUser(@RequestBody UserUpdateDto updateDto, @RequestParam Long id){
        ApiResponse apiResponse = userService.editUser(updateDto, id);
        return ResponseEntity.ok(apiResponse);
    }
}
