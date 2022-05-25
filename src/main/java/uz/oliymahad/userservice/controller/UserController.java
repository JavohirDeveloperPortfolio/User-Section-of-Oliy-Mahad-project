package uz.oliymahad.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.oliymahad.userservice.dto.request.DataPageRequest;
import uz.oliymahad.userservice.service.UserService;
import uz.oliymahad.userservice.service.oauth0.CustomOAuth0UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    private final CustomOAuth0UserService oAuth0UserService;

    @GetMapping()
    public ResponseEntity<?> userList(
            @RequestBody DataPageRequest dataPageRequest,
            @RequestParam(name = "search") String search
    ) {
        return ResponseEntity.ok(userService.list(dataPageRequest, search));
    }

}
