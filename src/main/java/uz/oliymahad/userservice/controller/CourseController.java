package uz.oliymahad.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.oliymahad.userservice.dto.request.CourseDto;
import uz.oliymahad.userservice.dto.response.RestAPIResponse;
import uz.oliymahad.userservice.service.CourseService;

import static uz.oliymahad.userservice.controller.BaseController.API;


@RestController
@RequestMapping(API+"/course")
@RequiredArgsConstructor
public class CourseController implements BaseController{

    private final CourseService courseService;

    @PostMapping(ADD)
    public ResponseEntity<?> addCourse (@RequestBody CourseDto courseDto) {
        RestAPIResponse apiResponse = courseService.add(courseDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @GetMapping(LIST)
    public ResponseEntity<?> getCourseList (
            @RequestParam Pageable pageable
    ) {
        return ResponseEntity.ok(courseService.getList(pageable));
    }

    @GetMapping(GET+LIST)
    public ResponseEntity<?> getCourses (Pageable pageable) {
        RestAPIResponse apiResponse = courseService.getList(pageable);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping(GET + "/{id}")
    public ResponseEntity<?> getCourse (@PathVariable Long id) {
        RestAPIResponse apiResponse = courseService.get(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @GetMapping(GET + "{name}")
    public ResponseEntity<?> getCourseByName (@RequestParam(name = "name") String name) {
        RestAPIResponse apiResponse = courseService.getByName(name);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @PutMapping(UPDATE+"/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @RequestBody CourseDto courseDto) {
        RestAPIResponse apiResponse = courseService.edit(id, courseDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @DeleteMapping(DELETE+"/{id}")
    public ResponseEntity<?> deleteCourse (@PathVariable Long id) {
        RestAPIResponse apiResponse = courseService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND).body(apiResponse);
    }
}