package uz.oliymahad.userservice.converter;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import uz.oliymahad.userservice.dto.response.UserDataResponse;
import uz.oliymahad.userservice.model.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;


public abstract class UserDataModelConverter {
    public static Page<UserDataResponse> converter(Page<UserEntity> source){
        ModelMapper modelMapper = new ModelMapper();

        List<UserDataResponse> list = source.getContent().size() > 0 ?
                source.getContent().stream().map(u -> modelMapper.map(u, UserDataResponse.class)).toList() :
                new ArrayList<>();
        return new PageImpl<>(list,source.getPageable(), source.getTotalPages());
    }
}
