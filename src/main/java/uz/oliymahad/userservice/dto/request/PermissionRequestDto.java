package uz.oliymahad.userservice.dto.request;

import lombok.*;
import uz.oliymahad.userservice.model.enums.ERole;

@AllArgsConstructor
@NoArgsConstructor
public class PermissionRequestDto {
    private boolean visibility;
    private boolean update;
    private boolean delete;
    private boolean info;

    public boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public boolean getDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean getUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean getInfo() {
        return info;
    }

    public void setInfo(boolean info) {
        this.info = info;
    }
}
