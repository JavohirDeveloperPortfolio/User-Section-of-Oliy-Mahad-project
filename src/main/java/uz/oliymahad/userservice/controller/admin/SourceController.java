package uz.oliymahad.userservice.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.oliymahad.userservice.dto.request.RoleRegisterRequest;
import uz.oliymahad.userservice.dto.request.UserRegisterRequest;
import uz.oliymahad.userservice.exception.custom_ex_model.UserAlreadyRegisteredException;
import uz.oliymahad.userservice.exception.custom_ex_model.UserAuthenticationException;
import uz.oliymahad.userservice.service.RoleService;
import uz.oliymahad.userservice.service.oauth0.CustomOAuth0UserService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/")
public class SourceController {
    private final RoleService roleService;
    private final CustomOAuth0UserService customOAuth0UserService;

    @PostMapping("/role_expansion")
    public ResponseEntity<?> addRole(
            @RequestBody @Valid RoleRegisterRequest request
    ){
        return ResponseEntity.ok(roleService.addRole(request.getRoleName()));
    }

    @PostMapping("/create_user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRegisterRequest userRegisterRequest){
        try {
            return ResponseEntity.ok(customOAuth0UserService.registerUser(userRegisterRequest));
        } catch (UserAlreadyRegisteredException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(UserAuthenticationException.class);
    }
}
