package uz.oliymahad.userservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.oliymahad.userservice.model.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select (count(u) > 0) from UserEntity u where u.phoneNumber = ?1")
    Boolean existsByPhoneNumber(String phoneNumber);
    @Query("select u from UserEntity u where u.email = ?1")
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByPhoneNumber(String phoneNumber);

    Page<UserEntity> findAllByPhoneNumberContainingIgnoreCaseOrEmailContainingIgnoreCaseOrUsernameContainingIgnoreCase(
            String phoneNumber,
            String email,
            String username,
            Pageable pageable
    );
}
