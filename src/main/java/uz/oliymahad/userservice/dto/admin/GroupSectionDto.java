package uz.oliymahad.userservice.dto.admin;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupSectionDto {

    private Long id;

    private String name ;

    private long membersCount ;

    private LocalDate startDate;

    private String gender;

    private String courseName;

    private long courseId ;
}
