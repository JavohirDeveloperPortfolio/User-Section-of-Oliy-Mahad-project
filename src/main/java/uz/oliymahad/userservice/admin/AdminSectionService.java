package uz.oliymahad.userservice.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.oliymahad.userservice.dto.response.RestAPIResponse;
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


    public RestAPIResponse getAdminSection(Long sectionId, Pageable pageable) {
        SectionDto sectionDto = getAccesses(sectionId);

        if(sectionDto == null)
            return new RestAPIResponse("Section not found",false,404);

        if (sectionId == sectionRepository.findByName("User").get().getId()) {
            AdminSectionDto<UserSectionDto> adminSectionDto = new AdminSectionDto<>();
            adminSectionDto.setHeaders(List.of("id","name","username","phoneNumber"));
            adminSectionDto.setBody(getUserListWithAccess(pageable));
            adminSectionDto.setVisibility(sectionDto.isVisibility());
            adminSectionDto.setEdit(sectionDto.isEdit());
            adminSectionDto.setDelete(sectionDto.isDelete());
            adminSectionDto.setInfo(sectionDto.isInfo());
            return new RestAPIResponse("User list",true,200,adminSectionDto);
        }
        else if(sectionId == sectionRepository.findByName("Course").get().getId()){
            AdminSectionDto<CourseSectionDto>  adminSectionDto = new AdminSectionDto<>();
            adminSectionDto.setHeaders(List.of("name","description","price","duration"));
            adminSectionDto.setBody(getCourseListWithAccess(pageable));
            adminSectionDto.setVisibility(sectionDto.isVisibility());
            return new RestAPIResponse("Course List",true,200,adminSectionDto);
        }
        return null;

    }

    public List<UserSectionDto> getUserListWithAccess(Pageable pageable) {

        List<UserSectionDto> userSectionDtos = new ArrayList<>();

        for (UserEntity userEntity : userRepository.findAll(pageable)) {
            UserSectionDto userSectionDto = new UserSectionDto();
            if(userEntity.getUsername() != null)
                userSectionDto.setUsername(userEntity.getUsername());
            userSectionDto.setId(userEntity.getId());
            if(userEntity.getUserRegisterDetails() != null && userEntity.getUserRegisterDetails().getFirstName() != null)
                userSectionDto.setName(userEntity.getUserRegisterDetails().getFirstName());
            if(userEntity.getPhoneNumber() != null)
                userSectionDto.setPhoneNumber(userEntity.getPhoneNumber());
            userSectionDtos.add(userSectionDto);
        }

        return userSectionDtos;

    }

    private SectionDto getAccesses( Long sectionId) {
        RoleEntity roleEntity = (RoleEntity) SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next();
        int roleId = roleEntity.getRoleName().id;
        Optional<Sections> optionalSections = sectionRepository.findById(sectionId);
        if(optionalSections.isEmpty())
            return null;

        Sections sections = optionalSections.get();

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

    private List<CourseSectionDto> getCourseListWithAccess(Pageable pageable){
        List<CourseSectionDto> courseList = courseFeign.getCourseList(pageable);
        return courseList;
    }
}
