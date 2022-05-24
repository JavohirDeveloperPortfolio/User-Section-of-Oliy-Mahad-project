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
@RequestMapping("/app-v0.0.1/ad/min/")
public class SourceController {
    private final RoleService roleService;

    @PostMapping("/role_expansion")
    public ResponseEntity<?> addRole(@Valid
            @RequestBody  RoleRegisterRequest request
    ){
        return ResponseEntity.ok(roleService.addRole(request.getRoleName()));
    }
}
