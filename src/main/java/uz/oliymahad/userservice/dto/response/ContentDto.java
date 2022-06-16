package uz.oliymahad.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentDto {
    private int ordinal;
    private String roleName;
    private SectionPermissionDto permissions;

    public static void main(String[] args) {
        HashMap<Integer,Integer> map = new HashMap<>();
    }
}
