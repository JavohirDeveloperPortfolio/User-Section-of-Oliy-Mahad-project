package uz.oliymahad.userservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.oliymahad.userservice.dto.request.PermissionRequestDto;
import uz.oliymahad.userservice.dto.request.RolePermission;
import uz.oliymahad.userservice.dto.request.SectionRequestDto;
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
import java.util.List;
import java.util.*;

import static uz.oliymahad.userservice.model.enums.ERole.*;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;
    private final JWTokenProvider jwTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;

    private Sections editSection(SectionRequestDto sectionRequestDto, Sections sections){
        int power = 0;

        for (RolePermission rolePermission : sectionRequestDto.getRolePermissionList()){
            if (rolePermission.getRoleName() == ROLE_USER.name()) power = 0;
            if (rolePermission.getRoleName() == ROLE_ADMIN.name()) power = 1;
            if (rolePermission.getRoleName() == ROLE_OWNER.name()) power = 2;

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

    public List<SectionDto> getSections(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        String token = headerAuth.substring(7);
        Jws<Claims> claimsJws = jwTokenProvider.validateJwtAccessToken(token);
        String subject = claimsJws.getBody().getSubject();


        UserEntity userDetails = (UserEntity) userDetailsService.loadUserByUsername(subject);
        List<SectionDto> result = new ArrayList<>();
        for (RoleEntity role : userDetails.getRoles()) {

            ERole roleName = role.getRoleName();
            for (Sections sections : sectionRepository.findAll()) {
                SectionDto sectionDto = new SectionDto();
                sectionDto.setName(sections.getName());

                if (roleName.equals(ROLE_USER)) {
                    sectionDto.setVisibility(((sections.getVisibility() & 1) > 0));
                    sectionDto.setDelete(((sections.getDelete() & 1) > 0));
                    sectionDto.setEdit(((sections.getUpdate() & 1) > 0));
                    sectionDto.setInfo(((sections.getInfo() & 1) > 0));
                }

                if (roleName.equals(ERole.ROLE_ADMIN)) {
                    sectionDto.setVisibility(((sections.getVisibility() & 2) > 0));
                    sectionDto.setDelete(((sections.getDelete() & 2) > 0));
                    sectionDto.setEdit(((sections.getUpdate() & 2) > 0));
                    sectionDto.setInfo(((sections.getInfo() & 2) > 0));
                }

                if (roleName.equals(ERole.ROLE_OWNER)) {
                    sectionDto.setVisibility(((sections.getVisibility() & 4) > 0));
                    sectionDto.setDelete(((sections.getDelete() & 4) > 0));
                    sectionDto.setEdit(((sections.getUpdate() & 4) > 0));
                    sectionDto.setInfo(((sections.getInfo() & 4) > 0));
                }
                result.add(sectionDto);
            }

        }

        return result;
    }
}
