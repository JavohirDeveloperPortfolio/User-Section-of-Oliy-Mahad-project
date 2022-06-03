package uz.oliymahad.userservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.oliymahad.userservice.model.enums.ERole;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PermissionRequestDto {
    private ERole roleName;
    private boolean visibility;
    private boolean editable;
    private boolean delete;
    private boolean update;
    private boolean info;
}
