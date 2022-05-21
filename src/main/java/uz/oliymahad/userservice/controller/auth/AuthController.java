package uz.oliymahad.userservice.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.oliymahad.userservice.dto.request.UserSignUpRequest;
import uz.oliymahad.userservice.dto.response.ApiResponse;
import uz.oliymahad.userservice.service.oauth0.CustomOAuth0UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    CustomOAuth0UserService oAuth0UserService;
    @GetMapping("/login/google")
    public ResponseEntity<?> loginWithGoogle(HttpServletResponse response){
        try {
            response.sendRedirect("/oauth2/authorization/google");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("success");
    }


    @GetMapping("/login/facebook")
    public ResponseEntity<?> loginWithFacebook(HttpServletResponse response){
        try {
            response.sendRedirect("/oauth2/authorization/facebook");
//            response.sendRedirect("/oauth2/callback/facebook");
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

    @PostMapping("/sign_up")
    public ResponseEntity<?> signUp(
            @RequestBody UserSignUpRequest userSignUpRequest
    ){
        return ResponseEntity.ok(oAuth0UserService.signUpUser(userSignUpRequest));
    }

    @PostMapping("/akdjndn1ad?dand/RE_dqkqekb?FR")
    public ResponseEntity<?> tokenRefresher(
            @RequestBody String jwtRefreshToken
    ){
        return ResponseEntity.ok(oAuth0UserService.validateRefreshToke(jwtRefreshToken));
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("Voooooola !!!!");
    }
}
