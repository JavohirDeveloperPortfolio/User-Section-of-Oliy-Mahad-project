package uz.oliymahad.userservice.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.oliymahad.userservice.dto.request.RoleRegisterRequest;
import uz.oliymahad.userservice.service.RoleService;
import uz.oliymahad.userservice.service.UserService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/")
public class SourceController {
    private final RoleService roleService;
    private final UserService userService;

    @PostMapping("/role_expansion")
    public ResponseEntity<?> addRole(
            @RequestBody @Valid RoleRegisterRequest request
    ){
        return ResponseEntity.ok(roleService.addRole(request.getRoleName()));
    }

    @PutMapping("/changeUserToAdmin/{id}")
    public ResponseEntity<?> changeUserToAdmin(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(userService.changeUserToAdmin(id));
    }
}
