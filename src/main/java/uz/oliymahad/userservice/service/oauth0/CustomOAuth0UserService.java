package uz.oliymahad.userservice.service.oauth0;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.oliymahad.userservice.dto.request.UserRegisterRequest;
import uz.oliymahad.userservice.exception.custom_ex_model.UserAlreadyRegisteredException;
import uz.oliymahad.userservice.exception.custom_ex_model.UserRoleNotFoundException;
import uz.oliymahad.userservice.model.entity.UserEntity;
import uz.oliymahad.userservice.model.enums.ERole;
import uz.oliymahad.userservice.repository.RoleRepository;
import uz.oliymahad.userservice.repository.UserRepository;
import uz.oliymahad.userservice.security.jwt.UserDetailsServiceImpl;
import uz.oliymahad.userservice.security.jwt.payload.response.JWTokenResponse;
import uz.oliymahad.userservice.security.jwt.JWTokenProvider;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class CustomOAuth0UserService {
    private final UserRepository repository;
    private final JWTokenProvider JWTokenProvider;

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;
    private final ModelMapper modelMapper;

    private final RoleRepository roleRepository;

    public JWTokenResponse registerUser(UserRegisterRequest userRegisterRequest) throws UserAlreadyRegisteredException {
        String[] tokens;
        UserEntity user = new UserEntity();
        try {
            userRegisterRequest.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
            user.setRoles(new HashSet<>() {{
                add(roleRepository.findByRoleName(ERole.ROLE_USER).orElseThrow(
                        () -> new UserRoleNotFoundException("role not found")
                ));
            }});
            modelMapper.map(userRegisterRequest, user);
            tokens = JWTokenProvider.generateJwtTokens(repository.save(user));
        } catch (Exception e) {
            throw new UserAlreadyRegisteredException(userRegisterRequest.getPhoneNumber() + " - already registered");
        }

        return new JWTokenResponse(0, tokens[0], tokens[1]);
    }


    public String[] validateRefreshToken(String jwtRefreshToken) throws RuntimeException {
        Jws<Claims> jws = JWTokenProvider.validateJwtRefreshToken(jwtRefreshToken);
        UserEntity user = userDetailsService.loadUserByUsername(jws.getBody().getSubject());
        return new String[]{JWTokenProvider.generateAccessToken(user), jwtRefreshToken};
    }
}
