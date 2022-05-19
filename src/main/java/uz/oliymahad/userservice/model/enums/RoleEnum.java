package uz.oliymahad.userservice.model.enums;


import org.assertj.core.util.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.*;
import static uz.oliymahad.userservice.model.enums.PermissionEnum.*;

public enum RoleEnum {

    GUEST(newHashSet()),
    USER(newHashSet()),
    STUDENT(newHashSet()),
    TEACHER(newHashSet()),
    ADMIN(newHashSet(TEACHER_CREATE));


    private final Set<PermissionEnum> permissions;

    RoleEnum(Set<PermissionEnum> permissions) {
        this.permissions = permissions;
    }

    public Set<PermissionEnum> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }

}
