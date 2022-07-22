package uz.oliymahad.userservice.scheduled;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import uz.oliymahad.userservice.service.GroupService;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class Scheduled {

    private final GroupService groupService;


    @org.springframework.scheduling.annotation.Scheduled(fixedRate = 120000L)
    private void fillGroup(){
        groupService.fillGroup();

    }


}
