package uz.oliymahad.userservice.service.oauth0;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import uz.oliymahad.userservice.dto.request.UserLoginRequest;
import uz.oliymahad.userservice.dto.request.UserRegisterRequest;
import uz.oliymahad.userservice.dto.response.SectionAccessResponse;
import uz.oliymahad.userservice.exception.custom_ex_model.UserAlreadyRegisteredException;
import uz.oliymahad.userservice.exception.custom_ex_model.UserInvalidPasswordException;
import uz.oliymahad.userservice.exception.custom_ex_model.UserNotFoundException;
import uz.oliymahad.userservice.model.entity.RoleEntity;
import uz.oliymahad.userservice.model.entity.UserEntity;
import uz.oliymahad.userservice.model.enums.ERole;
import uz.oliymahad.userservice.repository.UserRepository;
import uz.oliymahad.userservice.security.jwt.JWTokenProvider;
import uz.oliymahad.userservice.security.jwt.UserDetailsServiceImpl;
import uz.oliymahad.userservice.security.jwt.payload.response.JWTokenResponse;
import uz.oliymahad.userservice.service.SectionService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class CustomOAuth0UserService {

    private final static Logger logger = LoggerFactory.getLogger(CustomOAuth0UserService.class);
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;
    private final ModelMapper modelMapper;
    private final JWTokenProvider jwTokenProvider;
    private final SectionService sectionService;

    public JWTokenResponse registerUser(UserRegisterRequest userRegisterRequest)
            throws UserAlreadyRegisteredException {
        String[] tokens = new String[2];
        UserEntity user = new UserEntity();
        try {
            userRegisterRequest.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
            user.setRoles(new HashSet<>() {{
                add(new RoleEntity(1, ERole.ROLE_USER));
            }});
            modelMapper.map(userRegisterRequest, user);
            UserEntity entity = repository.save(user);
            tokens = jwTokenProvider.generateJwtTokens(entity);

        } catch (
                IllegalArgumentException | ClassCastException | IllegalStateException |
                        InvalidDataAccessApiUsageException e
        ) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            logger.warn(e.getMessage());
            throw new UserAlreadyRegisteredException(
                    "User already registered with : " + userRegisterRequest.getPhoneNumber()
            );
        }

        return new JWTokenResponse(OK.value(), OK.name(), tokens[0], tokens[1]);
    }

    public JWTokenResponse loginUser(UserLoginRequest userLoginRequest) {
        UserEntity user = repository.findByPhoneNumber(userLoginRequest.getPhoneNumber())
                .orElseThrow(
                        () -> new UserNotFoundException(
                                "User not found with : " + userLoginRequest.getPhoneNumber()
                        )
                );

        if (passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {

            String[] tokens = jwTokenProvider.generateJwtTokens(user);

            return new JWTokenResponse(OK.value(), OK.name(), tokens[0], tokens[1]);

        } else {

            throw new UserInvalidPasswordException("Wrong password");

        }


    }

    public String[] validateRefreshToken(String jwtRefreshToken) throws RuntimeException {
        Jws<Claims> jws = jwTokenProvider.validateJwtRefreshToken(jwtRefreshToken);
        UserEntity user = userDetailsService.loadUserByUsername(jws.getBody().getSubject());
        return new String[]{jwTokenProvider.generateAccessToken(user), jwtRefreshToken};
    }

    public long getUserByToken(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            headerAuth = headerAuth.substring(7);
            String subject = jwTokenProvider.validateJwtAccessToken(headerAuth)
                    .getBody()
                    .getSubject();
            UserEntity entity = userDetailsService.loadUserByUsername(subject);
            return entity.getId();
        }
        else throw new UserNotFoundException("User not found");
    }
}
