package uz.oliymahad.userservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import uz.oliymahad.userservice.model.entity.RoleEntity;
import uz.oliymahad.userservice.model.entity.UserRegisterDetails;
import uz.oliymahad.userservice.model.enums.EAuthProvider;
import java.sql.Timestamp;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDataResponse {

    private Long id;

    private String username;

    private String phoneNumber;

    private String email;

    private EAuthProvider provider;

    private String imageUrl;

    private Set<RoleEntity> roles;

    private UserRegisterDetails userRegisterDetails;

    private Timestamp createdAt;

}