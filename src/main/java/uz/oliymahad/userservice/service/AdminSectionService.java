package uz.oliymahad.userservice.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.oliymahad.userservice.dto.admin.AdminSectionDto;
import uz.oliymahad.userservice.dto.admin.CourseSectionDto;
import uz.oliymahad.userservice.dto.admin.UserSectionDto;
import uz.oliymahad.userservice.dto.response.RestAPIResponse;
import uz.oliymahad.userservice.dto.response.SectionPermissionDto;
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
    private final ModelMapper modelMapper;
//    private final CourseFeign courseFeign;


    public RestAPIResponse getSections (Long id, Pageable pageable) {
        Optional<Sections> optionalSections = sectionRepository.findById(id);
        if (optionalSections.isEmpty()) {
            return new RestAPIResponse("Finding item not found with  : " + id,false, HttpStatus.NOT_FOUND.value());
        }
        Sections sections = optionalSections.get();
        Object data;
        switch (sections.getName()) {
            case "users" :
                data = getUser(pageable,sections);
                break;
            case "course" :
                data = getCourse(pageable,sections);
                break;
            default :
                data = null;
        }
        return new RestAPIResponse("Data list", true , HttpStatus.OK.value(),data);
    }

    public AdminSectionDto<UserSectionDto> getUser(Pageable pageable, Sections sections) {
        Page<UserEntity> userEntities = userRepository.findAll(pageable);
        List<UserSectionDto> userSectionDtos = new ArrayList<>();
        for (UserEntity user : userEntities) {
            UserSectionDto userSectionDto = modelMapper.map(user, UserSectionDto.class);
            userSectionDtos.add(userSectionDto);
        }
        AdminSectionDto<UserSectionDto> adminSectionDto = new AdminSectionDto<>();
        adminSectionDto.setHeaders(List.of("id","phoneNumber"));
        adminSectionDto.setBody(userSectionDtos);
        adminSectionDto.setEdit(getPermission(sections).isUpdate());
        adminSectionDto.setDelete(getPermission(sections).isDelete());
        adminSectionDto.setInfo(getPermission(sections).isInfo());
        return adminSectionDto;
    }

    public AdminSectionDto<CourseSectionDto> getCourse (Pageable pageable, Sections sections) {
        AdminSectionDto<CourseSectionDto> adminSectionDto = new AdminSectionDto<>();
        adminSectionDto.setHeaders(List.of("name","description","price","duration"));
        adminSectionDto.setBody(null);
        adminSectionDto.setEdit(getPermission(sections).isUpdate());
        adminSectionDto.setDelete(getPermission(sections).isDelete());
        adminSectionDto.setInfo(getPermission(sections).isInfo());
        return adminSectionDto;
    }

    public SectionPermissionDto getPermission (Sections sections) {
        RoleEntity role = (RoleEntity) SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next();
        int number = role.getRoleName().getVal();
        SectionPermissionDto permission = new SectionPermissionDto();
        if (((1 << number) & sections.getUpdate()) > 0) {
            permission.setUpdate(true);
        }
        if (((1 << number) & sections.getDelete()) > 0) {
            permission.setDelete(true);
        }
        if (((1 << number) & sections.getInfo()) > 0) {
            permission.setInfo(true);
        }
        return permission;
    }
}
