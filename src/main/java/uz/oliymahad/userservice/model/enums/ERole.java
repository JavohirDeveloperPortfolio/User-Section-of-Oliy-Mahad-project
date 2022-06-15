package uz.oliymahad.userservice.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.management.relation.RoleNotFoundException;
import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ERole {
    ROLE_USER(1,0),
    ROLE_ADMIN(2, 1),
    ROLE_OWNER(3,2);

    public int id;
    private int val;


    ERole(int id) {
        this.id = id;
    }

    public static ERole getRoleUser(int roleId) throws RoleNotFoundException {
        return Arrays.stream(ERole.values()).filter(r -> r.id == roleId).findFirst().orElseThrow(
                () -> new RoleNotFoundException("Role not found with id - ")
        );
    }
}
