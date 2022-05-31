package uz.oliymahad.userservice.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.oliymahad.userservice.converter.UserDataModelConverter;
import uz.oliymahad.userservice.dto.request.UserUpdateRequest;
import uz.oliymahad.userservice.dto.response.UserDataResponse;
import uz.oliymahad.userservice.model.entity.UserEntity;
import uz.oliymahad.userservice.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public Page<?> list(String search, String[] categories, int page, int size, String order) {
        Page<UserEntity> list;
        if(categories == null && search == null)
             list = userRepository.findAll(
                    PageRequest.of(
                            page,
                            size
                    )
            );
        else if (categories != null && search == null) {
            list = userRepository.findAll(
                    PageRequest.of(
                            page,
                            size,
                            Sort.Direction.valueOf(order),
                            categories
                    ));
        } else if(categories == null)
            list = userRepository.
                    findAllByPhoneNumberContainingIgnoreCaseOrEmailContainingIgnoreCaseOrUsernameContainingIgnoreCase(
                            search,
                            search,
                            search,
                            PageRequest.of(page,size)
                    );

//        assert categories != null;
        else
            list = userRepository.
                findAllByPhoneNumberContainingIgnoreCaseOrEmailContainingIgnoreCaseOrUsernameContainingIgnoreCase(
                        search,
                        search,
                        search,
                        PageRequest.of(
                                page,
                                size,
                                Sort.Direction.valueOf(order),
                                categories
                        )
                );


        return UserDataModelConverter.converter(list);
    }

    public UserDataResponse updateUser(UserUpdateRequest userUpdateRequest) {

        return new UserDataResponse();
    }
}
