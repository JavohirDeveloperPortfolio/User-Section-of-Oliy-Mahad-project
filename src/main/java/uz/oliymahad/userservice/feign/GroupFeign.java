package uz.oliymahad.userservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import uz.oliymahad.userservice.dto.admin.CourseSectionDto;
import uz.oliymahad.userservice.dto.admin.GroupSectionDto;
import uz.oliymahad.userservice.dto.response.RestAPIResponse;

@FeignClient(name = "localhost",url = "http://localhost:8081")
public interface GroupFeign {
    @GetMapping("/api/v1/group/getGroups")
    RestAPIResponse getGroupPage (Pageable pageable);
}