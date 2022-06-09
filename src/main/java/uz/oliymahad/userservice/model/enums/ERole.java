package uz.oliymahad.userservice.model.enums;

import javax.management.relation.RoleNotFoundException;
import java.util.Arrays;

public enum ERole {
    ROLE_USER(1),
    ROLE_ADMIN(2),
    ROLE_OWNER(3);

    public int id;

    ERole(int id) {
        this.id = id;
    }

    public static ERole getRoleUser(int roleId) throws RoleNotFoundException {
        return Arrays.stream(ERole.values()).filter(r -> r.id == roleId).findFirst().orElseThrow(
                () -> new RoleNotFoundException("Role not found with id - ")
        );
    }
}
