package uz.oliymahad.userservice.dto.admin;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupSectionDto {

    private Long id;

    private String name ;

    private long membersCount ;

    private Date startDate;

    private long courseId ;
}
