package uz.oliymahad.userservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import uz.oliymahad.userservice.dto.response.RestAPIResponse;

@FeignClient(name = "localhost",url = "http://localhost:8081")
public interface CourseFeign {
    @GetMapping("/api/v1/course/get/list")
    RestAPIResponse getCourses (Pageable pageable);
}