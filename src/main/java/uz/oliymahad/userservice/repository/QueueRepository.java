package uz.oliymahad.userservice.repository;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.oliymahad.userservice.model.entity.queue.QueueEntity;


import java.util.List;

@Repository
public interface QueueRepository extends JpaRepository<QueueEntity,Long> {
    List<QueueEntity> findAllByUserId(long id);

    @Query(value = "select rank from (select user_id,applieddate, rank() over (order by applieddate) from queue_entity where course_id = :courseId) as sub where user_id = :userId", nativeQuery = true)
    List<Long> getUserCourseQueue(@Param("userId") Long userId, @Param("courseId") Long courseId);

    @Query(value = "select * from (select *,RANK() over(order by applieddate asc) from queue_entity q inner join users u on q.user_id = u.id inner join user_register_details urd on u.id = urd.user_id where q.course_id = :courseId and q.status = :status and urd.gender = :gender ) as sub  limit :limit", nativeQuery = true)
    List<QueueEntity> filterByCourseStatusGenderLimitForGroups(@Param("courseId") Long courseId, @Param("status") String status, @Param("gender") String gender, @Param("limit") int limit);


    @Query(value = "select *from filter_all_parameters1(i_user_id := :userId, i_gender := :gender, i_status := :status, i_course_id := :courseId)",nativeQuery = true)
    List<QueueEntity> getQueueByFilter(
            @Param("userId") Long userId,
            @Param("gender") String gender,
            @Param("status") String status,
            @Param("courseId") Long courseId

    );

//    @Query(value = "SELECT q FROM QueueEntity q WHERE q.status =: status and q.course.id =: courseId and q.user.userRegisterDetails.gender = :gender ORDER BY q.appliedDate ASC limit :limit ", nativeQuery = true)
//    List<QueueEntity> findAllByCourseEntityId(@Param("courseId") long courseId, @Param("status") String status, @Param("gender") String gender, @Param("membersCount") int limit);

}
