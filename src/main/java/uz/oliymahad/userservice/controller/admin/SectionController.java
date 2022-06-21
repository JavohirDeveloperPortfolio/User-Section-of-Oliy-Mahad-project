package uz.oliymahad.userservice.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.oliymahad.userservice.dto.request.SectionRequestDto;
import uz.oliymahad.userservice.dto.response.RestAPIResponse;
import uz.oliymahad.userservice.service.AdminSectionService;
import uz.oliymahad.userservice.service.SectionService;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/user/admin/section")
@RequiredArgsConstructor
public class SectionController {
    private final SectionService sectionService;
    private final AdminSectionService adminSectionService;

    @PostMapping("/edit")
    public Boolean addSection(@RequestBody  @Valid SectionRequestDto sectionRequestDto) {
        sectionService.addSection(sectionRequestDto);
        return true;
    }

    @GetMapping()
    public ResponseEntity<?> getAccessForSections() {

        return ResponseEntity.ok(sectionService.getAccessForSections());
    }
    @GetMapping("/get")
    public ResponseEntity<?> getSections() {
        return  ResponseEntity.ok(sectionService.getList());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getSectionById ( @PathVariable Long id) {
        RestAPIResponse restAPIResponse = sectionService.getSection(id);
        return ResponseEntity.status(restAPIResponse.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(restAPIResponse);
    }

    @GetMapping("/data")
    public ResponseEntity<?> getSections(
            @RequestParam Long id,
            Pageable pageable
    ){
        return ResponseEntity.ok().body(adminSectionService.getSections(id,pageable));
    }
}
