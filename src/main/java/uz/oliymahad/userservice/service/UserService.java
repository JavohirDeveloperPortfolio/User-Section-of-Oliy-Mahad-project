package uz.oliymahad.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.oliymahad.userservice.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Page list(Integer page, Integer size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }

    public Page list(Integer page, Integer size, String direction,String... sortBy){
        return userRepository.findAll(PageRequest.of(
                page,
                size,
                Sort.Direction.valueOf(direction),
                sortBy
        ));
    }
}
