package uz.oliymahad.userservice.dto.request;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SectionRequestDto {
    private String sectionName;
    private List<RolePermission> rolePermissionList;
}
