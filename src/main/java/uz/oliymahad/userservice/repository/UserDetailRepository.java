package uz.oliymahad.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.oliymahad.userservice.model.entity.UserRegisterDetails;

public interface UserDetailRepository extends JpaRepository<UserRegisterDetails, Long> {


}
