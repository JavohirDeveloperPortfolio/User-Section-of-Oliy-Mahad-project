package uz.oliymahad.userservice.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import uz.oliymahad.userservice.dto.request.SectionRequestDto;
import uz.oliymahad.userservice.model.entity.RoleEntity;
import uz.oliymahad.userservice.model.enums.ERole;
import uz.oliymahad.userservice.security.jwt.JWTokenProvider;
import uz.oliymahad.userservice.service.SectionService;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/get")
    public ResponseEntity<?> getSections(HttpServletRequest request) {
        return  ResponseEntity.ok(sectionService.getSections(request));
    }
}
