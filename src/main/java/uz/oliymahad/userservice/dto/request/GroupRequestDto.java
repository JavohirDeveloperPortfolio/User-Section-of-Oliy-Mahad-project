package uz.oliymahad.userservice.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class GroupRequestDto {

    private String name ;

    private int membersCount ;

    private String gender;

    private LocalDate startDate;

    private long courseId;


}
