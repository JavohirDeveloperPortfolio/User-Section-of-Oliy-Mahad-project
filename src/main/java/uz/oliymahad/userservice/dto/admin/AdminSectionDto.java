package uz.oliymahad.userservice.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminSectionDto<T> {
    private boolean delete;
    private boolean info;
    private boolean edit;
    private List<String> headers;
    private List<T> body;
}
