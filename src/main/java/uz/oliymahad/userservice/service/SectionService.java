package uz.oliymahad.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.oliymahad.userservice.dto.request.PermissionRequestDto;
import uz.oliymahad.userservice.dto.request.SectionRequestDto;
import uz.oliymahad.userservice.model.entity.Sections;
import uz.oliymahad.userservice.model.enums.ERole;
import uz.oliymahad.userservice.repository.SectionRepository;

import java.util.List;

@Service
public class SectionService {

    @Autowired
    SectionRepository sectionRepository;

    public void addSection(SectionRequestDto sectionRequestDto) {
        /**
         * admin - 2
         * s_admin - 4
         * user  - 1
         *
         *
         */

        Sections sections = new Sections();
        sections.setName(sectionRequestDto.getName());
        List<PermissionRequestDto> permissions = sectionRequestDto.getPermissions();
        permissions.forEach(pe -> {
            int vis = 0;
            int edit = 0;
            int delete = 0;
            int info= 0;

            if (pe.getRoleName().equals(ERole.ROLE_ADMIN.name()) && pe.isVisibility()) {
                vis += 2;
            }
            if (pe.getRoleName().equals(ERole.ROLE_USER.name()) && pe.isVisibility()) {
                vis += 0;
            }
            if (pe.getRoleName().equals(ERole.ROLE_OWNER.name()) && pe.isVisibility()) {
                vis += 4;
            }




            if (pe.getRoleName().equals(ERole.ROLE_ADMIN.name()) && pe.isEditable()) {
                edit += 2;
            }
            if (pe.getRoleName().equals(ERole.ROLE_USER.name()) && pe.isEditable()) {
                edit += 0;
            }
            if (pe.getRoleName().equals(ERole.ROLE_OWNER.name()) && pe.isEditable()) {
                edit += 4;
            }



            if (pe.getRoleName().equals(ERole.ROLE_ADMIN.name()) && pe.isDelete()) {
                delete += 2;
            }
            if (pe.getRoleName().equals(ERole.ROLE_USER.name()) && pe.isDelete()) {
                delete += 0;
            }
            if (pe.getRoleName().equals(ERole.ROLE_OWNER.name()) && pe.isDelete()) {
                delete += 4;
            }


            if (pe.getRoleName().equals(ERole.ROLE_ADMIN.name()) && pe.isInfo()) {
                info += 2;
            }
            if (pe.getRoleName().equals(ERole.ROLE_USER.name()) && pe.isInfo()) {
                info += 0;
            }
            if (pe.getRoleName().equals(ERole.ROLE_OWNER.name()) && pe.isInfo()) {
                info += 4;
            }

            sections.setEdit(edit);
            sections.setDelete(delete);
            sections.setInfo(info);
            sections.setVisibilty(vis);
        });
       sectionRepository.save(sections);
    }
}
