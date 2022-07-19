package uz.oliymahad.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.oliymahad.userservice.dto.request.GroupRequestDto;
import uz.oliymahad.userservice.dto.response.RestAPIResponse;
import uz.oliymahad.userservice.service.GroupService;

import static uz.oliymahad.userservice.controller.BaseController.API;


@RestController
@RequestMapping(API + "/group")
@RequiredArgsConstructor
public class GroupController implements BaseController{

    private final GroupService groupService ;

    @PostMapping("/add")
    public ResponseEntity<?> add (@RequestBody GroupRequestDto groupRequestDto) {
        RestAPIResponse apiResponse = groupService.addGroup(groupRequestDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @GetMapping("/getAllGroups")
    public ResponseEntity<?> getAllGroups () {
        RestAPIResponse apiResponse = groupService.getAllGroups();
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @GetMapping("/getGroups")
    public ResponseEntity<?> getGroupPage(Pageable pageable) {
        RestAPIResponse apiResponse = groupService.getGroups(pageable);
        return ResponseEntity.ok(apiResponse);
    }

}
