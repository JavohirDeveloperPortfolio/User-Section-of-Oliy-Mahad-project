package uz.oliymahad.userservice.service.oauth0;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.objectweb.asm.TypeReference;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import uz.oliymahad.userservice.dto.request.UserSignUpRequest;
import uz.oliymahad.userservice.exception.UserRoleNotFoundException;
import uz.oliymahad.userservice.exception.UserAlreadyRegisteredException;
import uz.oliymahad.userservice.model.entity.RoleEntity;
import uz.oliymahad.userservice.model.entity.UserEntity;
import uz.oliymahad.userservice.model.enums.ERole;
import uz.oliymahad.userservice.repository.RoleRepository;
import uz.oliymahad.userservice.repository.UserRepository;
import uz.oliymahad.userservice.security.jwt.UserDetailsServiceImpl;
import uz.oliymahad.userservice.security.jwt.payload.response.JwtResponse;
import uz.oliymahad.userservice.security.jwt.JwtProvider;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth0UserService {
    private final UserRepository repository;
    private final JwtProvider jwtProvider;

    private final UserDetailsServiceImpl userDetailsService;
    private final ModelMapper modelMapper;

    private final RoleRepository roleRepository;

    public JwtResponse signUpUser(UserSignUpRequest request) {
        Optional<UserEntity> entity = repository.findByPhoneNumber(request.getPhoneNumber());
        if (entity.isPresent())
            throw new UserAlreadyRegisteredException(request.getPhoneNumber() + " - already registered");

        UserEntity user = repository.save(modelMapper.map(request, UserEntity.class));
        user.setRoles(new HashSet<>() {{
            add(roleRepository.findByRoleName(ERole.ROLE_USER).orElseThrow(
                    () -> new UserRoleNotFoundException("role not found")
            ));
        }});
        String[] tokens = jwtProvider.generateJwtTokens(user);

        return new JwtResponse(0, tokens[0], tokens[1]);
    }


    public String[] validateRefreshToke(String jwtRefreshToken) throws RuntimeException {
        Jws<Claims> jws = jwtProvider.validateJwtRefreshToken(jwtRefreshToken);
        UserEntity user = userDetailsService.loadUserByUsername(jws.getBody().getSubject());
        return new String[]{jwtProvider.generateAccessToken(user),jwtRefreshToken};
    }
}
