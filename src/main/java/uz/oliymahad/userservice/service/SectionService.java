package uz.oliymahad.userservice.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.oliymahad.userservice.dto.request.SectionRequestDto;
import uz.oliymahad.userservice.dto.response.*;
import uz.oliymahad.userservice.model.entity.Sections;
import uz.oliymahad.userservice.model.enums.ERole;
import uz.oliymahad.userservice.repository.SectionRepository;
import uz.oliymahad.userservice.security.jwt.JWTokenProvider;
import uz.oliymahad.userservice.security.jwt.UserDetailsServiceImpl;

import java.util.List;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;
    private final JWTokenProvider jwTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final ModelMapper modelMapper;

    private Sections editSection(SectionRequestDto sectionRequestDto, Sections sections){
        int value = 0;
        if (sectionRequestDto.getPermissions0().get(0)) value |= 1 << 0;
        if (sectionRequestDto.getPermissions1().get(0)) value |= 1 << 1;
        if (sectionRequestDto.getPermissions2().get(0)) value |= 1 << 2;
        sections.setVisibility(value);

        value = 0;
        if (sectionRequestDto.getPermissions0().get(1)) value |= 1 << 0;
        if (sectionRequestDto.getPermissions1().get(1)) value |= 1 << 1;
        if (sectionRequestDto.getPermissions2().get(1)) value |= 1 << 2;
        sections.setEdit(value);

        value = 0;
        if (sectionRequestDto.getPermissions0().get(2)) value |= 1 << 0;
        if (sectionRequestDto.getPermissions1().get(2)) value |= 1 << 1;
        if (sectionRequestDto.getPermissions2().get(2)) value |= 1 << 2;
        sections.setDelete(value);

        value = 0;
        if (sectionRequestDto.getPermissions0().get(3)) value |= 1 << 0;
        if (sectionRequestDto.getPermissions1().get(3)) value |= 1 << 1;
        if (sectionRequestDto.getPermissions2().get(3)) value |= 1 << 2;
        sections.setInfo(value);

        return sections;
    }


    public void addSection(SectionRequestDto sectionRequestDto) {

        Optional<Sections> sections = sectionRepository.findByName(sectionRequestDto.getName());
        if (sections.isPresent()){
            sectionRepository.save(editSection(sectionRequestDto, sections.get()));
        }
        else {
            Sections sections1 = new Sections();
            sections1.setName(sectionRequestDto.getName());
            sectionRepository.save(editSection(sectionRequestDto, sections1));
        }
    }

    public RestAPIResponse getList() {
        List<Sections> sectionList = sectionRepository.findAll();
        List<SectionDto> sectionDtoList = new ArrayList<>();
        for (Sections sections: sectionList) {
            SectionDto sectionDto = modelMapper.map(sections, SectionDto.class);
            sectionDtoList.add(sectionDto);
        }
        return new RestAPIResponse("Section list",true, HttpStatus.OK.value(),sectionDtoList);
    }

    public RestAPIResponse getSection(Long id) {
        Optional<Sections> optionalSections = sectionRepository.findById(id);
        if (optionalSections.isEmpty()) {
            return new RestAPIResponse("Section not found", false,HttpStatus.NOT_FOUND.value());
        }
        Sections sections = optionalSections.get();
        SectionPermissionDto role_user = getPermissionToSection(ERole.ROLE_USER, sections);
        SectionPermissionDto role_owner = getPermissionToSection(ERole.ROLE_OWNER, sections);
        SectionPermissionDto role_admin = getPermissionToSection(ERole.ROLE_ADMIN, sections);
        ContentDto contentDto1 = new ContentDto(ERole.ROLE_USER.ordinal(), ERole.ROLE_USER.name(),role_user);
        ContentDto contentDto2 = new ContentDto(ERole.ROLE_ADMIN.ordinal(), ERole.ROLE_ADMIN.name(),role_admin);
        ContentDto contentDto3 = new ContentDto(ERole.ROLE_OWNER.ordinal(),ERole.ROLE_OWNER.name(),role_owner);
        List<ContentDto> contentDtoList = new ArrayList<>();
        contentDtoList.add(contentDto1);
        contentDtoList.add(contentDto2);
        contentDtoList.add(contentDto3);
        SectionResponse sectionResponse = new SectionResponse(sections.getId(),sections.getName(),contentDtoList);
        return new RestAPIResponse("Section",true,HttpStatus.OK.value(),sectionResponse);
    }

    public SectionPermissionDto getPermissionToSection (ERole role,Sections sections) {
        int number = role.ordinal();
        SectionPermissionDto permission = new SectionPermissionDto();
        if (((1 << number) & sections.getVisibility()) > 0) {
            permission.setVisibility(true);
        }else {
            permission.setVisibility(false);
        }
        if (((1 << number) & sections.getEdit()) > 0) {
            permission.setUpdate(true);
        }else {
            permission.setUpdate(false);
        }
        if (((1 << number) & sections.getDelete()) > 0) {
            permission.setDelete(true);
        }else {
            permission.setDelete(false);
        }
        if (((1 << number) & sections.getInfo()) > 0) {
            permission.setInfo(true);
        }else {
            permission.setInfo(false);
        }
        return permission;
    }





}
