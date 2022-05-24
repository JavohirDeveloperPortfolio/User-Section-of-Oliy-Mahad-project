package uz.oliymahad.userservice.service.oauth0;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.oliymahad.userservice.dto.request.UserLoginRequest;
import uz.oliymahad.userservice.dto.request.UserRegisterRequest;
import uz.oliymahad.userservice.exception.custom_ex_model.UserInvalidPasswordException;
import uz.oliymahad.userservice.exception.custom_ex_model.UserAlreadyRegisteredException;
import uz.oliymahad.userservice.exception.custom_ex_model.UserNotFoundException;
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
    
    private final static Logger logger = LoggerFactory.getLogger(CustomOAuth0UserService.class);
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;
    private final ModelMapper modelMapper;

    private final JWTokenProvider jwTokenProvider;
    private final RoleRepository roleRepository;

    public JWTokenResponse registerUser(UserRegisterRequest userRegisterRequest) throws UserAlreadyRegisteredException {
        String[] tokens = new String[2];
        UserEntity user = new UserEntity();
        try {
            userRegisterRequest.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
            user.setRoles(new HashSet<>() {{
                add(roleRepository.findByRoleName(ERole.ROLE_USER).orElseThrow(
                        () -> new UserRoleNotFoundException("ROLE NOT FOUND")
                ));
            }});
            modelMapper.map(userRegisterRequest, user);
            tokens = jwTokenProvider.generateJwtTokens(repository.save(user));
        }catch (IllegalArgumentException e){
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.warn(e.getMessage());
            throw new UserAlreadyRegisteredException(userRegisterRequest.getPhoneNumber() + " - ALREADY REGISTERED");
        }

        return new JWTokenResponse(0, tokens[0], tokens[1]);
    }

    public JWTokenResponse loginUser(UserLoginRequest userLoginRequest){
        UserEntity user = repository.findByPhoneNumber(userLoginRequest.getPhoneNumber())
                .orElseThrow(
                        () -> new UserNotFoundException(
                                "USER NOT FOUND WITH PHONE NUMBER - " + userLoginRequest.getPhoneNumber()
                                )
                );

        if(passwordEncoder.matches(userLoginRequest.getPassword(),user.getPassword())){
            String[] tokens = jwTokenProvider.generateJwtTokens(user);
            return new JWTokenResponse(0, tokens[0],tokens[1]);
        }else {
            throw new UserInvalidPasswordException("WRONG PASSWORD ENTERED");
        }


    }

    public String[] validateRefreshToken(String jwtRefreshToken) throws RuntimeException {
        Jws<Claims> jws = jwTokenProvider.validateJwtRefreshToken(jwtRefreshToken);
        UserEntity user = userDetailsService.loadUserByUsername(jws.getBody().getSubject());
        return new String[]{jwTokenProvider.generateAccessToken(user), jwtRefreshToken};
    }
}
