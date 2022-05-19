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
            String s = roleName.toUpperCase();
            if(s.contains("ROLE_") && s.indexOf("ROLE_") == 0) return roleRepository.save(new RoleEntity(ERole.valueOf(roleName)));
            return roleRepository.save(new RoleEntity(ERole.valueOf("ROLE_"+s)));
        }catch (RuntimeException e){
            logger.error(e.getMessage());
        }
        return null;
    }
}
