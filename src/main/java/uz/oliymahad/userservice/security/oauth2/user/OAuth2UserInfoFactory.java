package uz.oliymahad.userservice.security.oauth2.user;

import uz.oliymahad.userservice.exception.OAuth2AuthenticationProcessingException;
import uz.oliymahad.userservice.model.enums.EAuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(EAuthProvider.google.name())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(EAuthProvider.facebook.name())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
