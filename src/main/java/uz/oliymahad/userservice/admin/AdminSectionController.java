package uz.oliymahad.userservice.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.oliymahad.userservice.feign.CourseFeign;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/adminSection")
public class AdminSectionController {

    private final AdminSectionService adminSectionService;
    private final CourseFeign courseFeign;

    @GetMapping
    public ResponseEntity<?> getSections(
           Pageable pageable,
           @RequestParam String sectionName
    ){
        return ResponseEntity.ok().body(adminSectionService.getAdminSection(sectionName,pageable));
    }



}
