package uz.oliymahad.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.oliymahad.userservice.dto.request.PermissionRequestDto;
import uz.oliymahad.userservice.dto.request.SectionRequestDto;
import uz.oliymahad.userservice.model.entity.Sections;
import uz.oliymahad.userservice.model.enums.ERole;
import uz.oliymahad.userservice.repository.SectionRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
        AtomicInteger vis = new AtomicInteger();
        AtomicInteger edit = new AtomicInteger();
        AtomicInteger delete = new AtomicInteger();
        AtomicInteger info= new AtomicInteger();
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
}
