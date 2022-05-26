package uz.oliymahad.userservice.audit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Auditable<T> {
    //    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDate creationTimestamp;

    @UpdateTimestamp
    private LocalDate updateTimestamp;

    @CreatedBy
    private T createdBy;

    @LastModifiedBy
    private T updatedBy;
}
