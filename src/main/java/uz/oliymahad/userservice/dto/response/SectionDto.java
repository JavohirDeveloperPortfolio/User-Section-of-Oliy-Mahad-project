package uz.oliymahad.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SectionDto {
    private String name;
    private boolean role;
    private boolean edit;
    private boolean delete;
    private boolean info;

}
