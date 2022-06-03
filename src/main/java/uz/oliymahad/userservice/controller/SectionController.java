package uz.oliymahad.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.oliymahad.userservice.dto.request.SectionRequestDto;
import uz.oliymahad.userservice.service.SectionService;

@RestController
@RequestMapping("api/v1/section")
@RequiredArgsConstructor
public class SectionController {
    private final SectionService sectionService;

    @PostMapping
    public  Boolean addSection(@RequestBody SectionRequestDto sectionRequestDto){
        sectionService.addSection(sectionRequestDto);
        return true;
    }
}
