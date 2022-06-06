package uz.oliymahad.userservice.model.entity;

import lombok.*;
import uz.oliymahad.userservice.model.enums.ERole;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Sections {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private int  visibility;
    private int edit ;
    private int delete;
    private int info;

}
