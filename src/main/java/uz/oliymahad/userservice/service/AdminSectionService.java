package uz.oliymahad.userservice.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.oliymahad.userservice.dto.admin.*;
import uz.oliymahad.userservice.dto.response.*;
import uz.oliymahad.userservice.model.entity.RoleEntity;
import uz.oliymahad.userservice.model.entity.Sections;
import uz.oliymahad.userservice.model.entity.UserEntity;
import uz.oliymahad.userservice.model.entity.UserRegisterDetails;
import uz.oliymahad.userservice.repository.SectionRepository;
import uz.oliymahad.userservice.repository.UserDetailRepository;
import uz.oliymahad.userservice.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminSectionService implements Section {

    private final SectionRepository sectionRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserDetailRepository userDetailRepository;
    private final CourseService courseService;
    private final GroupService groupService;
    private final QueueService queueService;

    private final static int ZERO = 0;


    public RestAPIResponse getSections(Long id, Pageable pageable) {
        Optional<Sections> optionalSections = sectionRepository.findById(id);
        if (optionalSections.isEmpty()) {
            return new RestAPIResponse("Finding item not found with : " + id, false, HttpStatus.NO_CONTENT.value());
        }
        Sections sections = optionalSections.get();
        AdminSectionDto adminSectionDto = new AdminSectionDto();
        switch (sections.getName()) {
            case USER:
                adminSectionDto = getUsers(pageable, sections);
                break;
            case GROUP:
                adminSectionDto = getGroup(pageable, sections);
                break;
            case COURSE:
                adminSectionDto = getCourse(pageable, sections);
                break;
            case QUEUE:
                adminSectionDto = getQueue(pageable, sections);
                break;
        }
        return new RestAPIResponse("Data list", true, HttpStatus.OK.value(), adminSectionDto);
    }

    public AdminSectionDto getUsers(Pageable pageable, Sections sections) {
        Page<UserEntity> userEntities = userRepository.findAll(pageable);
        List<UserSectionDto> list = userEntities.getContent().size() > 0 ?
                userEntities.getContent().stream().map(u -> modelMapper.map(u, UserSectionDto.class)).toList() :
                new ArrayList<>();
        PageImpl<UserSectionDto> userSectionDtos = new PageImpl<>(list, userEntities.getPageable(), userEntities.getTotalPages());
        for (UserSectionDto userSectionDto : userSectionDtos) {
            Optional<UserRegisterDetails> optional = userDetailRepository.findByUserId(userSectionDto.getId());
            optional.ifPresent(userRegisterDetails -> modelMapper.map(userRegisterDetails, userSectionDto));
        }
        AdminSectionDto adminSectionDto = new AdminSectionDto();
        adminSectionDto.setHeaders(List.of("id", "firstName", "lastName", "middleName", "phoneNumber"));
        adminSectionDto.setBody(userSectionDtos);
        modelMapper.map(getPermission(sections), adminSectionDto);
        return adminSectionDto;
    }

    public AdminSectionDto getCourse(Pageable pageable, Sections sections) {
        AdminSectionDto adminSectionDto = new AdminSectionDto();
        adminSectionDto.setHeaders(List.of("id", "name", "description", "price", "duration"));
        RestAPIResponse apiResponse = courseService.getList(pageable);
        adminSectionDto.setBody(apiResponse.getData());
        modelMapper.map(getPermission(sections), adminSectionDto);
        return adminSectionDto;
    }

    public AdminSectionDto getGroup(Pageable pageable, Sections sections) {
        AdminSectionDto adminSectionDto = new AdminSectionDto();
        adminSectionDto.setHeaders(List.of("id", "name", "memberCount", "startDate", "courseName", "courseId"));
        RestAPIResponse apiResponse = groupService.getGroups(pageable);
        adminSectionDto.setBody(apiResponse.getData());
        modelMapper.map(getPermission(sections), adminSectionDto);
        return adminSectionDto;
    }

    public AdminSectionDto getQueue(Pageable pageable, Sections sections) {
        AdminSectionDto adminSectionDto = new AdminSectionDto();
        adminSectionDto.setHeaders(List.of("id", "userId", "firstName", "lastName", "phoneNumber", "courseName", "appliedDate", "endDate"));
        RestAPIResponse apiResponse = queueService.getQueueDetails(pageable);
        adminSectionDto.setBody(apiResponse.getData());
        modelMapper.map(getPermission(sections), adminSectionDto);
        return adminSectionDto;
    }

    public SectionPermissionDto getPermission(Sections sections) {
        SecurityContext context = SecurityContextHolder.getContext();
        UserEntity principal = (UserEntity) context.getAuthentication().getPrincipal();
        int number = principal.getRoles().stream().mapToInt(roleEntity -> (1 << roleEntity.getRoleName().getVal())).sum();
        SectionPermissionDto permissionDto = new SectionPermissionDto();
        if ((number & sections.getUpdate()) > ZERO) {
            permissionDto.setUpdate(true);
        }
        if ((number & sections.getInfo()) > ZERO) {
            permissionDto.setInfo(true);
        }
        if ((number & sections.getDelete()) > ZERO) {
            permissionDto.setDelete(true);
        }
        return permissionDto;
    }
}
