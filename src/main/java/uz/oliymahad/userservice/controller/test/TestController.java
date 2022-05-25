package uz.oliymahad.userservice.controller.test;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    @GetMapping("/test")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("Voooooola !!!!");
    }
}
