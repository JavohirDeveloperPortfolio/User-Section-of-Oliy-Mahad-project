package uz.oliymahad.userservice.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.oliymahad.userservice.dto.request.DataPageRequest;
import uz.oliymahad.userservice.dto.request.UserUpdateRequest;
import uz.oliymahad.userservice.dto.response.RestAPIResponse;
import uz.oliymahad.userservice.model.entity.UserEntity;
import uz.oliymahad.userservice.repository.UserRepository;

import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public Page list(DataPageRequest dataPageRequest, String search) throws IllegalArgumentException{

        int page = dataPageRequest.getPage() == null ? 0 : dataPageRequest.getPage();
        int size = dataPageRequest.getSize() == null ? 15 : dataPageRequest.getSize();
        var direction = dataPageRequest.getDirection().equals("ASC") ? ASC : DESC;

        if(dataPageRequest.getProperties() == null && search == null)
            return userRepository.findAll(
                    PageRequest.of(
                            page,
                            size
                    )
            );

        if(dataPageRequest.getProperties() == null && search != null)
            return userRepository.
                    findAllByPhoneNumberContainingIgnoreCaseOrEmailContainingIgnoreCaseOrUsernameContainingIgnoreCase(
                            search,
                            search,
                            search,
                            PageRequest.of(page,size)
                    );

        return userRepository.
                findAllByPhoneNumberContainingIgnoreCaseOrEmailContainingIgnoreCaseOrUsernameContainingIgnoreCase(
                        search,
                        search,
                        search,
                        PageRequest.of(
                                page,
                                size,
                                direction,
                                dataPageRequest.getProperties()
                        )
                );
    }

    public Page<UserEntity> getPage(int page, int size){
        PageRequest pageable = PageRequest.of(page, 2);
        Page<UserEntity> entityPage = userRepository.findAll(pageable);
        return entityPage;
    }

    public RestAPIResponse editUser(UserUpdateRequest updateRequest, long id){
        Optional<UserEntity> entityOptional = userRepository.findById(id);

        if(entityOptional.isPresent()){
            UserEntity entity = mapper.map(updateRequest, UserEntity.class);
            entity.setId(id);
            userRepository.save(entity);
            return new RestAPIResponse("Successfully updated!", true, HttpStatus.OK);

        }
        return new RestAPIResponse("Wrong id", false, HttpStatus.NO_CONTENT);
    }
}
