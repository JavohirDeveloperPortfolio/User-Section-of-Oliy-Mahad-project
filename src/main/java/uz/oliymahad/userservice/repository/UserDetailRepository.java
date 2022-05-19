package uz.oliymahad.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.oliymahad.userservice.model.entity.UserDetail;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {


}
