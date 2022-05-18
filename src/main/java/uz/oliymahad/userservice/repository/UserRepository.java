package uz.oliymahad.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.oliymahad.userservice.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByPhoneNumber(String phoneNumber);
}
