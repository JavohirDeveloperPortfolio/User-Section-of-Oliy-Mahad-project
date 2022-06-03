package uz.oliymahad.userservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Permission;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SectionRequestDto {
    private String name;
    private List<PermissionRequestDto> permissions;
}
