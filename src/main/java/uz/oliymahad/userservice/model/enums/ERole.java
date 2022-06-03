package uz.oliymahad.userservice.model.enums;

import javax.management.relation.RoleNotFoundException;
import java.util.Arrays;

public enum ERole {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_OWNER;

//    public final int id;
//
//    ERole(int id) {
//        this.id = ERole.values().length;
//    }

//    public static ERole getRoleUser(int roleId) throws RoleNotFoundException {
//        return Arrays.stream(ERole.values()).filter(r -> r.id == roleId).findFirst().orElseThrow(
//                () -> new RoleNotFoundException("Role not found with id - ")
//        );
//    }
}
