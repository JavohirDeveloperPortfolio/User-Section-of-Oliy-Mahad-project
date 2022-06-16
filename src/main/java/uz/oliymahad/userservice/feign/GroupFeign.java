package uz.oliymahad.userservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import uz.oliymahad.userservice.dto.admin.CourseSectionDto;
import uz.oliymahad.userservice.dto.admin.GroupSectionDto;

@FeignClient(name = "localhost",url = "http://localhost:8081")
public interface GroupFeign {
    @GetMapping("/api/group/getGroupPage")
    Page<GroupSectionDto> getGroupList(Pageable pageable);
}