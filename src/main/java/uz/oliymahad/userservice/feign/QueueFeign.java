package uz.oliymahad.userservice.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import uz.oliymahad.userservice.dto.response.RestAPIResponse;

@FeignClient(name = "localhost", url = "http://localhost:8081")
public interface QueueFeign {
    @GetMapping("/api/v1/course/queue/list")
    RestAPIResponse getQueue(Pageable pageable);
}