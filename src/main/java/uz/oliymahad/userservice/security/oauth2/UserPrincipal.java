package uz.oliymahad.userservice.security.oauth2;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import uz.oliymahad.userservice.model.entity.UserEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
@Getter
@Setter
public class UserPrincipal implements OAuth2User, UserDetails {


    private Long id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrincipal(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(UserEntity user) {
//        List<GrantedAuthority> authorities = Collections.
//                singletonList(new SimpleGrantedAuthority("ROLE_"+user.getRole().name()));

        List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority("ROLE_USER"));


        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public static UserPrincipal create(UserEntity user, Map<String, Object> attributes){
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getName() {
        return String.valueOf(this.id);
    }
}
