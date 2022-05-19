package uz.oliymahad.userservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.oliymahad.userservice.audit.Auditable;
import uz.oliymahad.userservice.model.enums.EAuthProvider;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEntity extends Auditable<String> implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;


    private String username;

    @Column(unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private EAuthProvider provider;

    private String providerId;

    @Column(unique = true)
    private String imageUrl;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<RoleEntity> roles;


    @OneToOne
    private UserRegisterDetails userRegisterDetails;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getUsername() {
        return this.phoneNumber != null ? this.phoneNumber : this.email;
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
}

