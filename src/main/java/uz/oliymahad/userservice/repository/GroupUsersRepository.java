package uz.oliymahad.userservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.oliymahad.userservice.model.entity.group.GroupUsersEntity;

public interface GroupUsersRepository extends JpaRepository<GroupUsersEntity, Long> {
}
