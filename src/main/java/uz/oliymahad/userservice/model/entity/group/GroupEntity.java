package uz.oliymahad.userservice.model.entity.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.oliymahad.userservice.audit.Auditable;
import uz.oliymahad.userservice.model.entity.course.CourseEntity;
import uz.oliymahad.userservice.model.enums.EGender;
import uz.oliymahad.userservice.model.enums.GroupStatusEnum;


import javax.persistence.*;
import java.util.Date;
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

    private String name;

    private Long membersCount ;

    @Enumerated
    private EGender type ;

    @Enumerated
    private GroupStatusEnum groupStatus ;

    private Date startDate;

    @ManyToOne
    private CourseEntity course ;

    @OneToMany(mappedBy = "group")
    private List<GroupUsersEntity> groupUsers;

}
