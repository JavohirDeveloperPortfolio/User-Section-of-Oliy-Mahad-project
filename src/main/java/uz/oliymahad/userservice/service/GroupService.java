package uz.oliymahad.userservice.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.oliymahad.userservice.dto.admin.GroupSectionDto;
import uz.oliymahad.userservice.dto.request.GroupRequestDto;
import uz.oliymahad.userservice.dto.response.Response;
import uz.oliymahad.userservice.dto.response.RestAPIResponse;
import uz.oliymahad.userservice.model.entity.UserEntity;
import uz.oliymahad.userservice.model.entity.course.CourseEntity;
import uz.oliymahad.userservice.model.entity.group.GroupEntity;
import uz.oliymahad.userservice.model.enums.EGender;
import uz.oliymahad.userservice.model.enums.GroupStatusEnum;
import uz.oliymahad.userservice.model.enums.Status;
import uz.oliymahad.userservice.repository.CourseRepository;
import uz.oliymahad.userservice.repository.GroupRepository;
import uz.oliymahad.userservice.repository.QueueRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService implements Response {

    private final GroupRepository groupRepository;

    private final CourseRepository courseRepository;

    private final QueueService queueService;

    private final QueueRepository queueRepository;

    private final ModelMapper modelMapper;

    private final UserService userService;

    public RestAPIResponse addGroup(GroupRequestDto groupRequestDto) {
        Optional<CourseEntity> optionalCourse = courseRepository.findById(groupRequestDto.getCourseId());

        if (optionalCourse.isEmpty()) {
            return new RestAPIResponse(COURSE + NOT_FOUND, false, 404);
        }
        GroupEntity groupEntity = new GroupEntity();
        modelMapper.map(groupRequestDto, groupEntity);
        groupEntity.setCourse(optionalCourse.get());
        groupEntity.setGroupStatus(GroupStatusEnum.IN_PROGRESS);
        groupEntity.setUserEntities(queueService.getUsers(groupRequestDto.getCourseId(), "PENDING", groupRequestDto.getMembersCount(), groupRequestDto.getGender()));
        groupRepository.save(groupEntity);
        return new RestAPIResponse(SUCCESSFULLY_SAVED, true, 200);
    }

    public RestAPIResponse getUserDetails(long userId) {
        RestAPIResponse apiResponse = userService.getUserDetails(userId);
        if (!apiResponse.isSuccess()) {
            return new RestAPIResponse(USER + NOT_FOUND, false, 404);
        }
        return new RestAPIResponse("User Register Details", true, 200, apiResponse.getData());
    }

    public RestAPIResponse getGroups(final Pageable page) {
        final Page<GroupEntity> groupEntities = groupRepository.findAll(page);
        final List<GroupSectionDto> list = groupEntities.getContent().size() > 0 ?
                groupEntities.getContent().stream().map(u -> modelMapper.map(u, GroupSectionDto.class)).toList() :
                new ArrayList<>();
        PageImpl<GroupSectionDto> groupResponseDtos = new PageImpl<>(list, groupEntities.getPageable(), groupEntities.getTotalPages());
        return new RestAPIResponse(DATA_LIST, true, 200, groupResponseDtos);
    }

    public RestAPIResponse getGroupUsers(long id) {
        Optional<GroupEntity> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isEmpty()) {
            return new RestAPIResponse(GROUP + NOT_FOUND, false, 404);
        }
        return new RestAPIResponse(DATA_LIST, true, 200, optionalGroup.get().getUserEntities());
    }

    public RestAPIResponse updateGroup(long id, GroupRequestDto groupRequestDto) {
        Optional<GroupEntity> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isEmpty()) {
            return new RestAPIResponse(GROUP + NOT_FOUND, false, 404);
        }
        GroupEntity groupEntity = optionalGroup.get();
        modelMapper.map(groupRequestDto, groupEntity);
        groupRepository.save(groupEntity);
        return new RestAPIResponse(SUCCESSFULLY_UPDATED, true, 200);
    }

    @Scheduled(fixedRate = 60 * 1000 * 5L)
    public void fillGroup() {
        List<GroupEntity> groupEntityList = groupRepository.findAll();

        for (GroupEntity groupEntity : groupEntityList) {
            if (groupEntity.getGroupStatus().equals(GroupStatusEnum.UPCOMING)) {
                List<UserEntity> newUsersForGroup = getNewUsersForGroup(groupEntity.getUserEntities(), groupEntity.getCourse().getId());
                groupEntity.setUserEntities(newUsersForGroup);
                groupRepository.save(groupEntity);
            }
        }
    }

    private List<UserEntity> getNewUsersForGroup(List<UserEntity> userEntityList, Long courseId) {
        List<UserEntity> removedUsers = new ArrayList<>();
        for (UserEntity user : userEntityList) {
            if (queueRepository.getStatus(user.getId()).equals(Status.REJECT)) {
                removedUsers.add(user);
            }
        }
        if (removedUsers.size() > 0) {
            List<UserEntity> users = queueService.getUsers(courseId, "PENDING", removedUsers.size(), userEntityList.get(0).getUserRegisterDetails().getGender().toString());
            userEntityList.removeAll(removedUsers);
            userEntityList.addAll(users);
        }
        return userEntityList;
    }
}


