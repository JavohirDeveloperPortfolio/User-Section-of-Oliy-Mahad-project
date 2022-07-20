package uz.oliymahad.userservice.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.oliymahad.userservice.model.entity.UserEntity;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
  @Service
  @Component
public class JWTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTokenFilter.class);
    private final UserDetailsServiceImpl userDetailsService;
    private final JWTokenProvider jwTokenProvider;
    private final ObjectMapper objectMapper;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);

            if (jwt == null) {
                filterChain.doFilter(request, response);
                return;
            }

            Jws<Claims> claimsJws = jwTokenProvider.validateJwtAccessToken(jwt);
            if (claimsJws != null) {

                String subject = claimsJws.getBody().getSubject();

                UserEntity userEntity = userDetailsService.loadUserByUsername(subject);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userEntity, null,
                        userEntity.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            else throw new RuntimeException("Jwt parse error");
        } catch (Exception e) {
            response.setStatus(UNAUTHORIZED.value());
            response
                    .getWriter()
                    .write(objectMapper.writeValueAsString(new JwtException("Token expired")));
              return;
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
