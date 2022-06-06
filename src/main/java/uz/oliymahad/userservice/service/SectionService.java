package uz.oliymahad.userservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.oliymahad.userservice.dto.request.PermissionRequestDto;
import uz.oliymahad.userservice.dto.request.SectionRequestDto;
import uz.oliymahad.userservice.dto.response.SectionDto;
import uz.oliymahad.userservice.model.entity.RoleEntity;
import uz.oliymahad.userservice.model.entity.Sections;
import uz.oliymahad.userservice.model.entity.UserEntity;
import uz.oliymahad.userservice.model.enums.ERole;
import uz.oliymahad.userservice.repository.SectionRepository;
import uz.oliymahad.userservice.security.jwt.JWTokenProvider;
import uz.oliymahad.userservice.security.jwt.UserDetailsServiceImpl;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;
    private final JWTokenProvider jwTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;

    private Sections editSection(SectionRequestDto sectionRequestDto, Sections sections){
        int value = 0;
        if (sectionRequestDto.getPermissions0().get(0)) value |= 1 << 0;
        if (sectionRequestDto.getPermissions1().get(0)) value |= 1 << 1;
        if (sectionRequestDto.getPermissions2().get(0)) value |= 1 << 2;
        sections.setVisibility(value);

        value = 0;
        if (sectionRequestDto.getPermissions0().get(1)) value |= 1 << 0;
        if (sectionRequestDto.getPermissions1().get(1)) value |= 1 << 1;
        if (sectionRequestDto.getPermissions2().get(1)) value |= 1 << 2;
        sections.setEdit(value);

        value = 0;
        if (sectionRequestDto.getPermissions0().get(2)) value |= 1 << 0;
        if (sectionRequestDto.getPermissions1().get(2)) value |= 1 << 1;
        if (sectionRequestDto.getPermissions2().get(2)) value |= 1 << 2;
        sections.setDelete(value);

        value = 0;
        if (sectionRequestDto.getPermissions0().get(3)) value |= 1 << 0;
        if (sectionRequestDto.getPermissions1().get(3)) value |= 1 << 1;
        if (sectionRequestDto.getPermissions2().get(3)) value |= 1 << 2;
        sections.setInfo(value);

        return sections;
    }


    public void addSection(SectionRequestDto sectionRequestDto) {

        Optional<Sections> sections = sectionRepository.findByName(sectionRequestDto.getName());
        if (sections.isPresent()){
            sectionRepository.save(editSection(sectionRequestDto, sections.get()));
        }
        else {
            Sections sections1 = new Sections();
            sections1.setName(sectionRequestDto.getName());
            sectionRepository.save(editSection(sectionRequestDto, sections1));
        }
    }
}
