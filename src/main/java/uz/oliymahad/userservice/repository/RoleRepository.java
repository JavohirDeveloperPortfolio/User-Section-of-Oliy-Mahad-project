package uz.oliymahad.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.oliymahad.userservice.model.entity.RoleEntity;
import uz.oliymahad.userservice.model.enums.ERole;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByRoleName(ERole eRole);
}
