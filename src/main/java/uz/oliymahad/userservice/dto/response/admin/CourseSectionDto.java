package uz.oliymahad.userservice.dto.response.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseSectionDto {
    private String name;
    private String description;
    private double price;
    private float duration;

    private boolean delete;
    private boolean info;
    private boolean edit;
}
