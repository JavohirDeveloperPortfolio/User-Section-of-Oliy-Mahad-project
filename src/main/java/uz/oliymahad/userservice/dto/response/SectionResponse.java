package uz.oliymahad.userservice.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SectionResponse {

    private String sectionName;
    private List<ContentDto> content;

}