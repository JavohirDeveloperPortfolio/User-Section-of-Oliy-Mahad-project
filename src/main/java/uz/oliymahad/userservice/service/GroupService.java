package uz.oliymahad.userservice.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.oliymahad.userservice.dto.admin.GroupSectionDto;
import uz.oliymahad.userservice.dto.request.FilterQueueForGroupsDTO;
import uz.oliymahad.userservice.dto.request.GroupRequestDto;
import uz.oliymahad.userservice.dto.response.RestAPIResponse;
import uz.oliymahad.userservice.model.entity.course.CourseEntity;
import uz.oliymahad.userservice.model.entity.group.GroupEntity;
import uz.oliymahad.userservice.model.entity.group.GroupUsersEntity;
import uz.oliymahad.userservice.model.enums.EGender;
import uz.oliymahad.userservice.model.enums.GroupStatusEnum;
import uz.oliymahad.userservice.model.enums.Status;
import uz.oliymahad.userservice.repository.CourseRepository;
import uz.oliymahad.userservice.repository.GroupRepository;
import uz.oliymahad.userservice.repository.GroupUsersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    private final CourseRepository courseRepository;

    private final QueueService queueService;

    private final GroupUsersRepository groupUsersRepository;

    private final ModelMapper modelMapper;


    public RestAPIResponse addGroup(GroupRequestDto groupRequestDto) {

        Optional<CourseEntity> courseEntity = courseRepository.findById(groupRequestDto.getCourseId());

        if (courseEntity.isEmpty()) {
            return new RestAPIResponse("course not found", true,404);
        }

        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setName(groupRequestDto.getName());
        groupEntity.setMembersCount(groupRequestDto.getMembersCount());
        groupEntity.setType(EGender.valueOf(groupRequestDto.getType()));
        groupEntity.setGroupStatus(GroupStatusEnum.UPCOMING);
        groupEntity.setStartDate(groupRequestDto.getStartDate());
        groupEntity.setCourse(courseEntity.get());

        GroupEntity groupEntityDB = groupRepository.save(groupEntity);

        FilterQueueForGroupsDTO filterQueueDTO = new FilterQueueForGroupsDTO();
        filterQueueDTO.setCourseId(groupRequestDto.getCourseId());
        filterQueueDTO.setGender(groupRequestDto.getType());
        filterQueueDTO.setLimit(groupRequestDto.getMembersCount());
        filterQueueDTO.setStatus(Status.ACCEPT.toString());
        RestAPIResponse usersByFilter = queueService.getUsersByFilter(filterQueueDTO);

        List<GroupUsersEntity> groupUsers = new ArrayList<>();
        for (long userId : (List<Long>)usersByFilter.getData()) {
            GroupUsersEntity groupUser = new GroupUsersEntity();
//            groupUser.setUserId(userId);
            groupUser.setGroup(groupEntityDB);
            GroupUsersEntity groupUserDB = groupUsersRepository.save(groupUser);
            groupUsers.add(groupUserDB);
        }

        groupEntityDB.setGroupUsers(groupUsers);
        groupRepository.save(groupEntityDB);


        groupRepository.save(groupEntity);

        return new RestAPIResponse("group added \n", true,200);
    }

    public RestAPIResponse getAllGroups(){
        List<GroupEntity> allGroup = groupRepository.findAll();
        return new RestAPIResponse("All Groups", true,200, allGroup) ;
    }

    public Page<GroupEntity> getGroupPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10);
        Page<GroupEntity> groupPage = groupRepository.findAll(pageable);
        return groupPage;
    }

    public Page<GroupEntity> getGroupPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<GroupEntity> groupPage = groupRepository.findAll(pageable);
        return groupPage;
    }

    public  RestAPIResponse getGroups(Pageable page) {
        Page<GroupEntity> groupEntities = groupRepository.findAll(page);
        List<GroupSectionDto> list = groupEntities.getContent().size() > 0 ?
                groupEntities.getContent().stream().map(u -> modelMapper.map(u, GroupSectionDto.class)).toList() :
                new ArrayList<>();
        PageImpl<GroupSectionDto> groupResponseDtos = new PageImpl<>(list, groupEntities.getPageable(), groupEntities.getTotalPages());
        return new RestAPIResponse("Group List",true,200,groupResponseDtos);
    }

    public RestAPIResponse getGroupById(Long id){
        Optional<GroupEntity> groupEntity = groupRepository.findById(id);
        return new RestAPIResponse("One Group", true,200, groupEntity) ;
    }

    public RestAPIResponse deleteGroupById(Long id){
        groupRepository.deleteById(id);
        return new RestAPIResponse("group deleted", true,200) ;
    }

    public RestAPIResponse editGroupInfo(GroupRequestDto newGroup, Long id){
        GroupEntity oldGroup = groupRepository.getById(id);
        if (!newGroup.getName().isBlank()) oldGroup.setName(newGroup.getName());
        if (newGroup.getMembersCount() != 0) oldGroup.setMembersCount(newGroup.getMembersCount());
        if (!newGroup.getType().isBlank()) oldGroup.setType(EGender.valueOf(newGroup.getType()));
        if (newGroup.getStartDate() != null) oldGroup.setStartDate(newGroup.getStartDate());
        if (newGroup.getCourseId() != 0) {
            Optional<CourseEntity> courseEntity = courseRepository.findById(newGroup.getCourseId());
            oldGroup.setCourse(courseEntity.get());
        }
        groupRepository.save(oldGroup) ;
        return new RestAPIResponse("group updated", true,200);
    }

}
