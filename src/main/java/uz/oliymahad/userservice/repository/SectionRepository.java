package uz.oliymahad.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.oliymahad.userservice.model.entity.Sections;

import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Sections , Long> {

    Optional<Sections> findByName(String  name);

}
