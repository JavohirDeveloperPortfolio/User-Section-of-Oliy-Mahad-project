package uz.oliymahad.userservice.security.oauth2.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();

}
