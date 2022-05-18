package uz.oliymahad.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.oliymahad.userservice.model.RoleEntity;
import uz.oliymahad.userservice.model.enums.ERole;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    RoleEntity findByRoleName(ERole eRole);
}
