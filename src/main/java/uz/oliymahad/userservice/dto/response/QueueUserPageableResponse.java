package uz.oliymahad.userservice.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
@Setter
public class QueueUserPageableResponse {
    private int totalPages;

    private int totalElements;

    private int number;

    private int size;

    private int numberOfElements;

    private List<QueueUserDetailsDTO> content;

    private boolean hasContent;

    private boolean isFirst;

    private boolean isLast;

    private Sort sort;
}
