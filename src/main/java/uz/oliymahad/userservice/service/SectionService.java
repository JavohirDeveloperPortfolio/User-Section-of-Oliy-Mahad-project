package uz.oliymahad.userservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;
import uz.oliymahad.userservice.dto.request.PermissionRequestDto;
import uz.oliymahad.userservice.dto.request.SectionRequestDto;
import uz.oliymahad.userservice.dto.response.SectionDto;
import uz.oliymahad.userservice.model.entity.RoleEntity;
import uz.oliymahad.userservice.model.entity.Sections;
import uz.oliymahad.userservice.model.entity.UserEntity;
import uz.oliymahad.userservice.model.enums.ERole;
import uz.oliymahad.userservice.repository.SectionRepository;
import uz.oliymahad.userservice.security.jwt.JWTokenProvider;
import uz.oliymahad.userservice.security.jwt.UserDetailsServiceImpl;
import uz.oliymahad.userservice.service.oauth2.CustomUserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;
    private final JWTokenProvider jwTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;

    public void addSection(SectionRequestDto sectionRequestDto) {

        Sections sections = new Sections();
        sections.setName(sectionRequestDto.getName());
        List<PermissionRequestDto> permissions = sectionRequestDto.getPermissions();
        AtomicInteger vis = new AtomicInteger();
        AtomicInteger edit = new AtomicInteger();
        AtomicInteger delete = new AtomicInteger();
        AtomicInteger info = new AtomicInteger();
        permissions.forEach(pe -> {
            if (pe.getRoleName().equals(ERole.ROLE_ADMIN) && pe.isVisibility()) {
                vis.addAndGet(2);
            }
            if (pe.getRoleName().equals(ERole.ROLE_USER) && pe.isVisibility()) {
                vis.addAndGet(0);
            }
            if (pe.getRoleName().equals(ERole.ROLE_OWNER) && pe.isVisibility()) {
                vis.addAndGet(4);
            }


            if (pe.getRoleName().equals(ERole.ROLE_ADMIN) && pe.isEditable()) {
                edit.addAndGet(2);
            }
            if (pe.getRoleName().equals(ERole.ROLE_USER) && pe.isEditable()) {
                edit.addAndGet(0);
            }
            if (pe.getRoleName().equals(ERole.ROLE_OWNER) && pe.isEditable()) {
                edit.addAndGet(4);
            }


            if (pe.getRoleName().equals(ERole.ROLE_ADMIN) && pe.isDelete()) {
                delete.addAndGet(2);
            }
            if (pe.getRoleName().equals(ERole.ROLE_USER) && pe.isDelete()) {
                delete.addAndGet(0);
            }
            if (pe.getRoleName().equals(ERole.ROLE_OWNER) && pe.isDelete()) {
                delete.addAndGet(4);
            }


            if (pe.getRoleName().equals(ERole.ROLE_ADMIN) && pe.isInfo()) {
                info.addAndGet(2);
            }
            if (pe.getRoleName().equals(ERole.ROLE_USER) && pe.isInfo()) {
                info.addAndGet(0);
            }
            if (pe.getRoleName().equals(ERole.ROLE_OWNER) && pe.isInfo()) {
                info.addAndGet(4);
            }


        });
        sections.setEdit(edit.get());
        sections.setDelete(delete.get());
        sections.setInfo(info.get());
        sections.setVisibilty(vis.get());
        sectionRepository.save(sections);
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

                if (roleName.equals(ERole.ROLE_USER)) {
                    sectionDto.setVisibility(((sections.getVisibilty() & 1) > 0));
                    sectionDto.setDelete(((sections.getDelete() & 1) > 0));
                    sectionDto.setEdit(((sections.getEdit() & 1) > 0));
                    sectionDto.setInfo(((sections.getInfo() & 1) > 0));
                }

                if (roleName.equals(ERole.ROLE_ADMIN)) {
                    sectionDto.setVisibility(((sections.getVisibilty() & 2) > 0));
                    sectionDto.setDelete(((sections.getDelete() & 2) > 0));
                    sectionDto.setEdit(((sections.getEdit() & 2) > 0));
                    sectionDto.setInfo(((sections.getInfo() & 2) > 0));
                }

                if (roleName.equals(ERole.ROLE_OWNER)) {
                    sectionDto.setVisibility(((sections.getVisibilty() & 4) > 0));
                    sectionDto.setDelete(((sections.getDelete() & 4) > 0));
                    sectionDto.setEdit(((sections.getEdit() & 4) > 0));
                    sectionDto.setInfo(((sections.getInfo() & 4) > 0));
                }
                result.add(sectionDto);
            }

        }

        return result;
    }
}
