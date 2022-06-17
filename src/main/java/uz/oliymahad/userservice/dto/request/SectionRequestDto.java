package uz.oliymahad.userservice.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SectionRequestDto {

    @NotBlank private String sectionName;

    @JsonProperty("content")
    private List<RolePermission> rolePermissionList;
}
