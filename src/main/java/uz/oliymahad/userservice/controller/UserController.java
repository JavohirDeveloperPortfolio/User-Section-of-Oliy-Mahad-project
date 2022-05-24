package uz.oliymahad.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.oliymahad.userservice.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<?> userList(
            @RequestParam(name = "page",defaultValue = "0") Integer page ,
            @RequestParam(name = "size", defaultValue = "20") Integer size
    ){
        return ResponseEntity.ok(userService.list(page, size));
    }

    @GetMapping("/sort")
    public ResponseEntity<?> userList(
            @RequestParam(name = "page",defaultValue = "0", required = false) Integer page ,
            @RequestParam(name = "size", defaultValue = "20", required = false) Integer size,
            @RequestParam(name = "direction", defaultValue = "DESC", required = false) String direction,
            @RequestBody String[] sortBy
    ){
        return ResponseEntity.ok(userService.list(page, size, direction, sortBy));
    }


}
