package uz.oliymahad.userservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.oliymahad.userservice.security.jwt.JWTokenEntryPoint;
import uz.oliymahad.userservice.security.jwt.JWTokenFilter;
import uz.oliymahad.userservice.security.jwt.JWTokenProvider;
import uz.oliymahad.userservice.security.oauth2.UserPrincipal;
import uz.oliymahad.userservice.service.oauth2.CustomOAuth2UserService;
import uz.oliymahad.userservice.service.oauth2.CustomUserDetailsService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true
)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final PasswordEncoder passwordEncoder;
    private final JWTokenProvider jwtProvider;
    private final JWTokenFilter jwTokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
            .disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling().authenticationEntryPoint(new JWTokenEntryPoint())
            .and()
            .addFilterBefore(jwTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers("/api/v1/auth/**").permitAll()
            .antMatchers("/api/v1/user/**").permitAll()
            .antMatchers("/app/v1/admin/**").permitAll()
            .antMatchers("/swagger-ui.html**", "/swagger-resources/**",
                "/v2/api-docs**", "/webjars/**", "/swagger-ui/**").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .oauth2Login()
//                .loginPage("/api/auth/login")
            .userInfoEndpoint()
            .userService(customOAuth2UserService)
            .and()
            .successHandler(new AuthenticationSuccessHandler() {
                @Override
                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {



//                        DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
//
                    String accessToken = jwtProvider.generateAccessToken((UserPrincipal) authentication.getPrincipal());
                    String refreshToken = jwtProvider.generateRefreshToken((UserPrincipal) authentication.getPrincipal());
                    response.addHeader("access_token", accessToken);
                    response.addHeader("refresh_token", refreshToken);
                    System.out.println("hello world" + authentication.getPrincipal().toString());
                    String targetUrl = "/api/v1/auth/success";
                    RequestDispatcher dis = request.getRequestDispatcher(targetUrl);
                    dis.forward(request, response);
                }
            }).failureHandler(new AuthenticationFailureHandler() {
                @Override
                public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                                    AuthenticationException exception) throws IOException, ServletException {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                }
            });
//                .and().exceptionHandling().authenticationEntryPoint(new AuthEntryPointJwt()).and()

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
    }
}
