package uz.oliymahad.enity.enums;


import org.assertj.core.util.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static uz.oliymahad.enity.enums.PermissionEnum.*;

public enum UserRoleEnum {

    GUEST(Sets.newHashSet()),
    USER(Sets.newHashSet()),
    STUDENT(Sets.newHashSet()),
    TEACHER(Sets.newHashSet()),
    ADMIN(Sets.newHashSet());


    private final Set<PermissionEnum> permissions;

    UserRoleEnum(Set<PermissionEnum> permissions) {
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
