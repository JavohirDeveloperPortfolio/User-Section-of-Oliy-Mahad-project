package uz.oliymahad.userservice.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class GroupRequestDto {

    private String name ;

    private int membersCount ;

    private String gender;

    private Date startDate ;

    private long courseId ;


}
