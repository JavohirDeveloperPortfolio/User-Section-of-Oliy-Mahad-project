package uz.oliymahad.userservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.oliymahad.userservice.model.enums.EGender;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user_register_details")
public class UserRegisterDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private UserEntity user;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private EGender gender;

    private String passport;

    private Date birthDate;

}
