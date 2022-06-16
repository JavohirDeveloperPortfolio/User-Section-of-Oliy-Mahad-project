package uz.oliymahad.userservice.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.oliymahad.userservice.service.AdminSectionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/adminSection")
public class AdminSectionController {

    private final AdminSectionService adminSectionService;
    @GetMapping()
    public ResponseEntity<?> getSections(
           @RequestParam Long id,
           Pageable pageable
    ){
        return ResponseEntity.ok().body(adminSectionService.getSections(id,pageable));
    }

}
