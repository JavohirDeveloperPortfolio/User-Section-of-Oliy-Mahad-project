package uz.oliymahad.userservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.oliymahad.userservice.model.entity.group.GroupEntity;

public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
//    List<GroupEntity> findGroupEntitiesByCreatedDateBetween(Date createdDate, Date createdDate2) ;
//    List<GroupEntity> findGroupEntitiesByCreatedDate_Month(int createdDate_month);
}
