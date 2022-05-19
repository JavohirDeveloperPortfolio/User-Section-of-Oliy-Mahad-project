package uz.oliymahad.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.oliymahad.userservice.dto.response.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    @GetMapping("/login/google")
    public ResponseEntity<?> loginWithGoogle(HttpServletRequest request, HttpServletResponse response){
        try {
            response.sendRedirect("/oauth2/authorization/google");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("success");
    }

    @GetMapping("/success")
    public ResponseEntity<?> signInSuccess(HttpServletResponse response) {
        return ResponseEntity.ok(
                new ApiResponse("JWT token" + response.getHeader("Authorization"), true, HttpStatus.OK)
        );
    }
}
