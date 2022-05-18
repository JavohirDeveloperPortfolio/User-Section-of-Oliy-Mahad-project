package uz.oliymahad.userservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import uz.oliymahad.userservice.model.entity.auth.AuthProviderEnum;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String email;

        @Column(nullable = false)
        private Boolean emailVerified = false;

        @JsonIgnore
        private String password;

        @Enumerated(EnumType.STRING)
        private AuthProviderEnum provider;

        private String providerId;

        private String imageUrl;
}
