package uz.oliymahad.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.oliymahad.userservice.dto.request.UserUpdateRequest;
import uz.oliymahad.userservice.dto.response.RestAPIResponse;
import uz.oliymahad.userservice.model.entity.RoleEntity;
import uz.oliymahad.userservice.model.entity.UserEntity;
import uz.oliymahad.userservice.model.entity.UserRegisterDetails;
import uz.oliymahad.userservice.model.entity.course.CourseEntity;
import uz.oliymahad.userservice.model.entity.queue.QueueEntity;
import uz.oliymahad.userservice.model.enums.EGender;
import uz.oliymahad.userservice.model.enums.ERole;
import uz.oliymahad.userservice.model.enums.Status;
import uz.oliymahad.userservice.repository.CourseRepository;
import uz.oliymahad.userservice.repository.QueueRepository;
import uz.oliymahad.userservice.repository.UserDetailRepository;
import uz.oliymahad.userservice.repository.UserRepository;
import uz.oliymahad.userservice.service.UserService;
import uz.oliymahad.userservice.service.oauth0.CustomOAuth0UserService;

import javax.validation.Valid;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final QueueRepository queueRepository;
    private final UserDetailRepository userDetailRepository;
    private final CourseRepository courseRepository;

    private final CustomOAuth0UserService oAuth0UserService;

//    @PreAuthorize(value = "hasAnyRole(\"ADMIN\",\"OWNER\")")
    @GetMapping()
    public ResponseEntity<?> userList(
            @RequestParam(name = "search",required = false) String search,
            @RequestParam(name = "categories", required = false) String[] categories,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20") Integer size,
            @RequestParam(name = "order",defaultValue = "DESC") String order

    ) {
        //TODO - DONE
        return ResponseEntity.ok(new RestAPIResponse(OK.name(),true, OK.value(), userService.list(
                search,
                categories,
                page,
                size,
                order
        )));
    }



    @PutMapping()
    public ResponseEntity<?> modifyUser(
            @RequestParam(required = true) Long id,
            @RequestBody @Valid UserUpdateRequest userUpdateRequest
    ){
        return ResponseEntity.ok(new RestAPIResponse(OK.name(), true, OK.value(),userService.updateUser(userUpdateRequest, id)));
    }


//    @GetMapping("/{phonenumber}")
//    public ResponseEntity<?> getUserByPhone(@PathVariable String phonenumber){
//        return ResponseEntity.ok( new RestAPIResponse(OK.name(), true , OK.value(),
//            userService.getByPhone(phonenumber)));
//
//    }

//    @PutMapping("/{userId}/auth")
//    @PreAuthorize(value = "hasAnyRole(\"ADMIN\")")
//    public ResponseEntity<?> updateUserRole(
//            @RequestParam(required = true) Integer roleId,
//            @PathVariable Long userId
//    ) throws RoleNotFoundException {
//        return ResponseEntity.ok(
//                new RestAPIResponse(
//                        OK.name(),
//                        true,
//                        OK.value(),
//                        userService.updateUserRole(userId, roleId)
//                )
//        );
//    }


    @GetMapping("/fake")
    public void fake() {
        for (int i = 0; i < 1_000; i++) {
            UserEntity userEntity = new UserEntity();
            userEntity.setRoles(Set.of(new RoleEntity(2, ERole.ROLE_ADMIN)));
            userEntity.setPhoneNumber(generator(6));
            userEntity.setPassword("root123");
            UserEntity savedUserEntity = userRepository.save(userEntity);

            UserRegisterDetails userRegisterDetails = new UserRegisterDetails();
            userRegisterDetails.setFirstName(generator(5));
            userRegisterDetails.setLastName(generator(5));
            if (i % 2 == 0) {
                userRegisterDetails.setGender(EGender.FEMALE);
            } else {
                userRegisterDetails.setGender(EGender.MALE);
            }
            userRegisterDetails.setUser(savedUserEntity);
            UserRegisterDetails save = userDetailRepository.save(userRegisterDetails);
            savedUserEntity.setUserRegisterDetails(save);
            userRepository.save(savedUserEntity);


            Optional<CourseEntity> optionalCourse = courseRepository.findById(1l);
            QueueEntity queueEntity = new QueueEntity();
            queueEntity.setUser(savedUserEntity);
            queueEntity.setStatus(Status.PENDING);
            queueEntity.setAppliedDate(LocalDateTime.now());
            queueEntity.setCourse(optionalCourse.get());
            queueRepository.save(queueEntity);
        }

    }

    private String generator(int range){
        String s = UUID.randomUUID().toString();
        s.substring(0,range).replace("_","");
        return s;
    }
}
