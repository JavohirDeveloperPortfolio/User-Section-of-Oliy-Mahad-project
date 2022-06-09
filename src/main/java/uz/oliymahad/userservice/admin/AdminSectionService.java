package uz.oliymahad.userservice.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.oliymahad.userservice.dto.response.admin.AdminSectionDto;
import uz.oliymahad.userservice.dto.response.admin.CourseSectionDto;
import uz.oliymahad.userservice.dto.response.SectionDto;
import uz.oliymahad.userservice.dto.response.admin.UserSectionDto;
import uz.oliymahad.userservice.feign.CourseFeign;
import uz.oliymahad.userservice.model.entity.RoleEntity;
import uz.oliymahad.userservice.model.entity.Sections;
import uz.oliymahad.userservice.model.entity.UserEntity;
import uz.oliymahad.userservice.repository.SectionRepository;
import uz.oliymahad.userservice.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminSectionService {

    private final SectionRepository sectionRepository;
    private final UserRepository userRepository;
    private final CourseFeign courseFeign;


    public AdminSectionDto getAdminSection(String sectionName, Pageable pageable) {

        SectionDto sectionDto = getAccesses(sectionName);
        if (sectionName.equals("User")) {
            AdminSectionDto<UserSectionDto> adminSectionDto = new AdminSectionDto<>();
            adminSectionDto.setHeaders(List.of("id","name","username","phoneNumber"));
            adminSectionDto.setBody(getUserListWithAccess(pageable,sectionDto));
            adminSectionDto.setVisibility(sectionDto.isVisibility());
            return adminSectionDto;
        }
        else if(sectionName.equals("Course")){
            AdminSectionDto<CourseSectionDto>  adminSectionDto = new AdminSectionDto<>();
            adminSectionDto.setHeaders(List.of("name","description","price","duration"));
            adminSectionDto.setBody(getCourseListWithAccess(pageable,sectionDto));
            adminSectionDto.setVisibility(sectionDto.isVisibility());
            return adminSectionDto;
        }
        return null;

    }

    public List<UserSectionDto> getUserListWithAccess(Pageable pageable,SectionDto sectionDto) {

        List<UserSectionDto> userSectionDtos = new ArrayList<>();

        for (UserEntity userEntity : userRepository.findAll(pageable)) {
            UserSectionDto userSectionDto = new UserSectionDto();
            userSectionDto.setUsername(userEntity.getUsername());
            userSectionDto.setId(userEntity.getId());
            userSectionDto.setName(userEntity.getUserRegisterDetails().getFirstName());
            userSectionDto.setPhoneNumber(userEntity.getPhoneNumber());
            userSectionDto.setDelete(sectionDto.isDelete());
            userSectionDto.setEdit(sectionDto.isEdit());
            userSectionDto.setInfo(sectionDto.isInfo());

            userSectionDtos.add(userSectionDto);
        }

        return userSectionDtos;

    }

    private SectionDto getAccesses( String sectionName) {
        RoleEntity roleEntity = (RoleEntity) SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next();
        int roleId = roleEntity.getRoleName().id;
        Optional<Sections> byName = sectionRepository.findByName(sectionName);
        Sections sections = byName.get();

        SectionDto sectionDto = new SectionDto();
        sectionDto.setName(sections.getName());

        if (((1 << roleId) & sections.getVisibility()) > 0)
            sectionDto.setVisibility(true);
        if (((1 << roleId) & sections.getUpdate()) > 0)
            sectionDto.setEdit(true);
        if (((1 << roleId) & sections.getInfo()) > 0)
            sectionDto.setInfo(true);
        if (((1 << roleId) & sections.getDelete()) > 0)
            sectionDto.setDelete(true);

        return sectionDto;
    }

    private List<CourseSectionDto> getCourseListWithAccess(Pageable pageable,SectionDto sectionDto){
        List<CourseSectionDto> courseList = courseFeign.getCourseList(pageable);
        for (CourseSectionDto courseSectionDto : courseList) {
            courseSectionDto.setEdit(sectionDto.isEdit());
            courseSectionDto.setDelete(sectionDto.isDelete());
            courseSectionDto.setInfo(sectionDto.isInfo());
        }
        return courseList;
    }
}
