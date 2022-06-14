package uz.oliymahad.userservice.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import uz.oliymahad.userservice.dto.request.SectionRequestDto;
import uz.oliymahad.userservice.dto.response.RestAPIResponse;
import uz.oliymahad.userservice.model.entity.RoleEntity;
import uz.oliymahad.userservice.model.enums.ERole;
import uz.oliymahad.userservice.security.jwt.JWTokenProvider;
import uz.oliymahad.userservice.service.SectionService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("api/v1/section")
@RequiredArgsConstructor
public class SectionController {
    private final SectionService sectionService;

    @PostMapping("/edit")
    public Boolean addSection(@RequestBody SectionRequestDto sectionRequestDto) {
        sectionService.addSection(sectionRequestDto);
        return true;
    }

    @GetMapping()
    public ResponseEntity<?> getAccessForSections() {

        return  ResponseEntity.ok(sectionService.getAccessForSections());
    @GetMapping("/get")
    public ResponseEntity<?> getSections() {
        return  ResponseEntity.ok(sectionService.getList());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getSectionById ( @PathVariable Long id) {
        RestAPIResponse restAPIResponse = sectionService.getSection(id);
        return ResponseEntity.status(restAPIResponse.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(restAPIResponse);
    }

}
