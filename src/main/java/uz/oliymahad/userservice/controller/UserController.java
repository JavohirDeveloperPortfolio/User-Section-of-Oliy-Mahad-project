package uz.oliymahad.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.oliymahad.userservice.dto.request.UserUpdateRequest;
import uz.oliymahad.userservice.dto.response.RestAPIResponse;
import uz.oliymahad.userservice.service.UserService;
import uz.oliymahad.userservice.service.oauth0.CustomOAuth0UserService;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    private final CustomOAuth0UserService oAuth0UserService;

    @GetMapping()
    public ResponseEntity<?> userList(
            @RequestParam(name = "search",required = false) String search,
            @RequestParam(name = "categories", required = false) String[] categories,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20") Integer size,
            @RequestParam(name = "order",defaultValue = "DESC") String order

    ) {
        //TODO - DONE
        return ResponseEntity.ok(new RestAPIResponse(OK.name(),true, OK.value(), userService.list(
                search,
                categories,
                page,
                size,
                order
        )));
    }

    @PostMapping()
    public ResponseEntity<?> modifyUser(
            @RequestParam(required = true) Long id,
            @RequestBody UserUpdateRequest userUpdateRequest
    ){
        return ResponseEntity.ok(new RestAPIResponse(OK.name(), true, OK.value(),userService.updateUser(userUpdateRequest)));
    }

}
