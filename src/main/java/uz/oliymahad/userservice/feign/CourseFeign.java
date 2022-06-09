package uz.oliymahad.userservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import uz.oliymahad.userservice.dto.response.admin.CourseSectionDto;

import java.util.List;

@FeignClient("COURCE-SERVICE")
@Component
public interface CourseFeign {

    @GetMapping("api/course/getCourseListWithPageable")
    List<CourseSectionDto> getCourseList(Pageable pageable);

}
