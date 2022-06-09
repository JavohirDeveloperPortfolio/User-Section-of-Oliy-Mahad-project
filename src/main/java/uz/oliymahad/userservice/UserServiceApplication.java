package uz.oliymahad.userservice;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import uz.oliymahad.userservice.model.entity.RoleEntity;
import uz.oliymahad.userservice.model.entity.UserEntity;
import uz.oliymahad.userservice.model.enums.ERole;
import uz.oliymahad.userservice.repository.RoleRepository;
import uz.oliymahad.userservice.repository.UserRepository;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        try {
            Arrays.stream(ERole.values()).forEach(r ->
                    roleRepository.save(new RoleEntity(r.id, r))
            );

            UserEntity entity = new UserEntity(
                    passwordEncoder.encode("123456789"),
                    "+998974022722",
                    new HashSet<>() {{
                        add(new RoleEntity(ERole.ROLE_OWNER.id, ERole.ROLE_OWNER));
                    }}

            );
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(entity.getEmail(), null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            userRepository.save(entity);

        } catch (Exception e) {
        }


    }
}
