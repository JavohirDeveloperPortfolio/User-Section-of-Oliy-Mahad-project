package uz.oliymahad.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import uz.oliymahad.userservice.model.enums.ERole;

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
    private ERole roleName;

    @Override
    public String getAuthority() {
        return roleName.name();
    }
}
