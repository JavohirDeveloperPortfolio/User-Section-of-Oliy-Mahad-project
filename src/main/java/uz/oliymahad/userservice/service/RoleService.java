package uz.oliymahad.userservice.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uz.oliymahad.userservice.model.entity.RoleEntity;
import uz.oliymahad.userservice.model.enums.ERole;
import uz.oliymahad.userservice.repository.RoleRepository;


@RequiredArgsConstructor
@Service
public class RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);
    private final RoleRepository roleRepository;

    public RoleEntity addRole(String roleName){
        try {
            return roleRepository.save(new RoleEntity(ERole.valueOf(roleName)));
        }catch (RuntimeException e){
            logger.error(e.getMessage());
        }
        return null;
    }
}
