package uz.oliymahad.userservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("COURCE-SERVICE")
@Component
public interface CourseFeign {
    @GetMapping("/api/course/get/list")
    ResponseEntity<?> getCourseList();
}
