package uz.oliymahad.userservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import uz.oliymahad.userservice.model.enums.ERole;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "role")
public class RoleEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private ERole roleName;

    public RoleEntity(ERole roleName) {
        this.roleName = roleName;
    }

//    @JsonIgnore
    @Override
    public String getAuthority() {
        return roleName.name();
    }

}
