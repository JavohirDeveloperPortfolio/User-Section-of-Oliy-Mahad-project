package uz.oliymahad.userservice.service;


import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.oliymahad.userservice.converter.UserDataModelConverter;
import uz.oliymahad.userservice.dto.request.ImageRequest;
import uz.oliymahad.userservice.dto.request.UserUpdateRequest;
import uz.oliymahad.userservice.dto.response.UserDataResponse;
import uz.oliymahad.userservice.exception.custom_ex_model.UserNotFoundException;
import uz.oliymahad.userservice.model.entity.RoleEntity;
import uz.oliymahad.userservice.exception.custom_ex_model.UserNotFoundException;
import uz.oliymahad.userservice.model.entity.UserEntity;
import uz.oliymahad.userservice.model.enums.ERole;
import uz.oliymahad.userservice.repository.UserRepository;

import javax.management.relation.RoleNotFoundException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final  String  baseImagePath = "/Users/ismoilovdavron/IdeaProjects/User-Section-of-Oliy-Mahad-project/images/avatar/";

    public Page<?> list(String search, String[] categories, int page, int size, String order) {
        Page<UserEntity> list;
        if (categories == null && search == null)
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
        } else if (categories == null)
            list = userRepository.
                    findAllByPhoneNumberContainingIgnoreCaseOrEmailContainingIgnoreCaseOrUsernameContainingIgnoreCase(
                            search,
                            search,
                            search,
                            PageRequest.of(page, size)
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

    public UserDataResponse updateUser(UserUpdateRequest userUpdateRequest, long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> {
            throw new UserNotFoundException(id + " user not found");
        });

        if (userUpdateRequest.getEmail() != null)
            userEntity.setEmail(userUpdateRequest.getEmail());

        if (userUpdateRequest.getPassword() != null)
            userEntity.setPassword(passwordEncoder.encode(
                userUpdateRequest.getPassword()
            ));

        if (userUpdateRequest.getPhoneNumber() != null)
            userEntity.setPhoneNumber(userUpdateRequest.getPhoneNumber());

        String save = imageSave(userUpdateRequest.getImage() , userEntity.getImageUrl());

        userEntity.setImageUrl(save);

        UserEntity save1 = userRepository.save(userEntity);

        return modelMapper.map(save1, UserDataResponse.class);

    }

    private String imageSave(ImageRequest imageRequest , String oldImageUrl) {
        byte[] de = Base64.decodeBase64(imageRequest.getContent());
        String uploadUrl = null;
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(de));
            uploadUrl = baseImagePath + UUID.randomUUID() + "." + imageRequest.getContentType();
            File f = new File(uploadUrl);
            ImageIO.write(image, imageRequest.getContentType(), f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(oldImageUrl!= null){
            File file = new File(oldImageUrl);
            file.delete();
        }


        return uploadUrl;
    }
}
