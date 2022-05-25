package uz.oliymahad.userservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
public class APIConfiguration {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties clientProperties){

        List<ClientRegistration> registrations =
                clientProperties.getRegistration().keySet().stream()
                        .map(provider -> getRegistration(clientProperties, provider))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String provider) {
        if("google".equals(provider)) {
            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration()
                    .get("google");

            return CommonOAuth2Provider.GOOGLE.getBuilder(provider)
                    .clientId(registration.getClientId())
                    .clientSecret(registration.getClientSecret())
                    .scope("email", "profile")
                    .build();
        }

        if("facebook".equals(provider)) {
            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration()
                    .get("facebook");

            return CommonOAuth2Provider.FACEBOOK.getBuilder(provider)
                    .clientId(registration.getClientId())
                    .clientSecret(registration.getClientSecret())
                    .userInfoUri("https://graph.facebook.com/me?fields=id,name,email,link")
                    .scope("email")
                    .build();
        }
        return null;

    }
}
