package uz.oliymahad.userservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.oliymahad.userservice.dto.request.RolePermission;
import uz.oliymahad.userservice.dto.request.SectionRequestDto;
import uz.oliymahad.userservice.dto.response.*;
import uz.oliymahad.userservice.dto.response.SectionAccessResponse;
import uz.oliymahad.userservice.dto.response.SectionDto;
import uz.oliymahad.userservice.model.entity.RoleEntity;
import uz.oliymahad.userservice.model.entity.Sections;
import uz.oliymahad.userservice.model.entity.UserEntity;
import uz.oliymahad.userservice.model.enums.ERole;
import uz.oliymahad.userservice.repository.SectionRepository;
import uz.oliymahad.userservice.security.jwt.JWTokenProvider;
import uz.oliymahad.userservice.security.jwt.UserDetailsServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static uz.oliymahad.userservice.model.enums.ERole.*;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;
    private final JWTokenProvider jwTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final ModelMapper modelMapper;

    private Sections editSection(SectionRequestDto sectionRequestDto, Sections sections){
        int power = 0;

        for (RolePermission rolePermission : sectionRequestDto.getRolePermissionList()){
            if (rolePermission.getRoleName().equals(ROLE_USER.name())) power = 0;
            if (rolePermission.getRoleName().equals(ROLE_ADMIN.name())) power = 1;
            if (rolePermission.getRoleName().equals(ROLE_OWNER.name())) power = 2;

            int permissionValue = 0;
            if (rolePermission.getPermissionRequestDto().getVisibility()) sections.setVisibility(sections.getVisibility() | 1 << power);
            if (rolePermission.getPermissionRequestDto().getUpdate()) sections.setUpdate(sections.getUpdate() | 1 << power);
            if (rolePermission.getPermissionRequestDto().getDelete()) sections.setDelete(sections.getDelete() | 1 << power);
            if (rolePermission.getPermissionRequestDto().getInfo()) sections.setInfo(sections.getInfo() | 1 << power);
        }
        return sections;
    }


    public void addSection(SectionRequestDto sectionRequestDto) {

        Optional<Sections> sections = sectionRepository.findByName(sectionRequestDto.getSectionName());
        if (sections.isPresent()){
            sectionRepository.save(editSection(sectionRequestDto, sections.get()));
        }
        else {
            Sections sections1 = new Sections();
            sections1.setName(sectionRequestDto.getSectionName());
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
        return new RestAPIResponse("Section",true, HttpStatus.OK.value(),sectionResponse);
    }

    public SectionPermissionDto getPermissionToSection (ERole role,Sections sections) {
        int number = role.ordinal() - 1;
        SectionPermissionDto permission = new SectionPermissionDto();
        if (((1 << number) & sections.getVisibility()) > 0) {
            permission.setVisibility(true);
        }else {
            permission.setVisibility(false);
        }
        if (((1 << number) & sections.getUpdate()) > 0) {
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

        return result;
    }

    public List<SectionAccessResponse> getAccessForSections(){
        Collection<? extends GrantedAuthority> authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        RoleEntity role = (RoleEntity) authorities.iterator().next();

        List<SectionAccessResponse> responseList = new ArrayList<>() ;
        int val = (int) Math.pow(2,role.getRoleName().getVal());
         for (Sections section : sectionRepository.findAll()) {
            if((section.getVisibility() & val) > 0){
                responseList.add(new SectionAccessResponse(section.getId(), section.getName()));
            }
        }
        return responseList;
    }
}
