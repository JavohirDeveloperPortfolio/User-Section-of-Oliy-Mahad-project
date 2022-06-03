package uz.oliymahad.userservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PermissionRequestDto {
    private String roleName;
    private boolean visibility;
    private boolean editable;
    private boolean delete;
    private boolean update;
    private boolean info;
}
