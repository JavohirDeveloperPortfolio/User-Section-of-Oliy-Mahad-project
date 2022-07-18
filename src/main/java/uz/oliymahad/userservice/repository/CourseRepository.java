package uz.oliymahad.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.oliymahad.userservice.model.entity.course.CourseEntity;


import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity,Long> {
    boolean existsByName(String name);
    Optional<CourseEntity> findByName(String name);
    boolean existsById(String id);
}
