package uz.oliymahad.userservice.model.entity;

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
import uz.oliymahad.userservice.model.enums.EAuthProvider;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

//    @Column(nullable = false, unique = true)
    private String phoneNumber;

//    @Column(nullable = false)
    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private EAuthProvider provider;

    private String providerId;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean emailVerified = false;


    @ManyToMany(fetch = FetchType.EAGER)
    private Set<RoleEntity> roles;

    @OneToOne
    private UserDetail userDetails;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updateAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getUsername() {
        return this.phoneNumber;
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

