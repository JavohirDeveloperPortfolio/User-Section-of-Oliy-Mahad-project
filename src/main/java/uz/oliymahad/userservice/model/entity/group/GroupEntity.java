package uz.oliymahad.userservice.model.entity.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.oliymahad.userservice.audit.Auditable;
import uz.oliymahad.userservice.model.entity.UserEntity;
import uz.oliymahad.userservice.model.entity.course.CourseEntity;
import uz.oliymahad.userservice.model.enums.EGender;
import uz.oliymahad.userservice.model.enums.GroupStatusEnum;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "group_entity")
public class GroupEntity extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String name;

    private int membersCount ;

    @Enumerated
    private GroupStatusEnum groupStatus ;

    private LocalDate startDate = LocalDate.now();

    @ManyToOne
    private CourseEntity course;

    @Enumerated(EnumType.STRING)
    private EGender gender;

    @OneToMany(fetch = FetchType.EAGER)
    private List<UserEntity> users;
}
