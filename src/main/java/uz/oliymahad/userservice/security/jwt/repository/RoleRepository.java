package uz.oliymahad.userservice.security.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.oliymahad.userservice.security.jwt.models.ERole;
import uz.oliymahad.userservice.security.jwt.models.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
