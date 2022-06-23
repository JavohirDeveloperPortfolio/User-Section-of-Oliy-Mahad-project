package uz.oliymahad.userservice.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.oliymahad.userservice.dto.request.RoleRegisterRequest;
import uz.oliymahad.userservice.service.RoleService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user/admin/")
public class SourceController {
    private final RoleService roleService;

    @PostMapping("/role_expansion")
    public ResponseEntity<?> addRole(
            @RequestBody @Valid RoleRegisterRequest request
    ){
        return ResponseEntity.ok(roleService.addRole(request.getRoleName()));
    }
}
