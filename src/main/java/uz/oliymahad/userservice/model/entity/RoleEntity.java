package uz.oliymahad.userservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import uz.oliymahad.userservice.model.enums.ERole;
import uz.oliymahad.userservice.model.enums.RoleEnum;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class RoleEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private RoleEnum roleName;

    @Override
    public String getAuthority() {
        return roleName.name();
    }
}
