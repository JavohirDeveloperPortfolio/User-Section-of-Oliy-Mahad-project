package uz.oliymahad.userservice.service.oauth0;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.oliymahad.userservice.dto.request.UserSignUpRequest;
import uz.oliymahad.userservice.exception.UserRoleNotFoundException;
import uz.oliymahad.userservice.exception.UserAlreadyRegisteredException;
import uz.oliymahad.userservice.model.entity.UserEntity;
import uz.oliymahad.userservice.model.enums.ERole;
import uz.oliymahad.userservice.repository.RoleRepository;
import uz.oliymahad.userservice.repository.UserRepository;
import uz.oliymahad.userservice.security.jwt.payload.response.JwtResponse;
import uz.oliymahad.userservice.security.jwt.JwtUtils;

import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth0UserService {
    private final UserRepository repository;
    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;

    private final RoleRepository roleRepository;
    public JwtResponse signUpUser(UserSignUpRequest request) {
        Optional<UserEntity> entity = repository.findByPhoneNumber(request.getPhoneNumber());
        if (entity.isPresent())
            throw new UserAlreadyRegisteredException(request.getPhoneNumber() + " - already registered");

        UserEntity user = repository.save(modelMapper.map(request, UserEntity.class));
        user.setRoles(new HashSet<>(){{add(roleRepository.findByRoleName(ERole.ROLE_USER).orElseThrow(
                () -> new UserRoleNotFoundException("role not found")
        ));}});
        String[] tokens = jwtUtils.generateJwtTokens(user);

        return new JwtResponse(0, tokens[0], tokens[1]);
    }

}
