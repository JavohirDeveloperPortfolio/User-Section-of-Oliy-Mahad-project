package uz.oliymahad.userservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.oliymahad.userservice.model.enums.ERole;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PermissionRequestDto {
    private boolean visibility;
    private boolean delete;
    private boolean update;
    private boolean info;
}
