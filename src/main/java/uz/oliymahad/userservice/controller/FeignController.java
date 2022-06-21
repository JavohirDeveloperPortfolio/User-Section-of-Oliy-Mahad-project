package uz.oliymahad.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.oliymahad.dto.request.UsersIDSRequest;
import uz.oliymahad.userservice.dto.response.UserDataResponse;
import uz.oliymahad.userservice.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/feign")
@RequiredArgsConstructor
public class FeignController {

    private final UserService userService;
    @PostMapping()
    public List<UserDataResponse> getUserByIds(
            @RequestBody UsersIDSRequest idsRequest
    ){
        return userService.getUsersByIds(idsRequest);
    }
}