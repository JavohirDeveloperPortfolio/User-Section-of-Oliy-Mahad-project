package uz.oliymahad.userservice.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.oliymahad.userservice.dto.response.RestAPIResponse;

@FeignClient(name = "localhost",url = "http://localhost:8081")
public interface QueueFeign {
    @GetMapping("/api/v1/queue/getQueue")
    RestAPIResponse getQueue(PageRequest pageRequest);
}