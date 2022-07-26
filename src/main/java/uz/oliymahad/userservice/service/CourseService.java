package uz.oliymahad.userservice.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.oliymahad.userservice.dto.admin.CourseSectionDto;
import uz.oliymahad.userservice.dto.request.CourseDto;
import uz.oliymahad.userservice.dto.response.Response;
import uz.oliymahad.userservice.dto.response.RestAPIResponse;
import uz.oliymahad.userservice.model.entity.course.CourseEntity;
import uz.oliymahad.userservice.repository.CourseRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CourseService implements BaseService<CourseDto,Long, CourseEntity, Pageable> , Response {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Override
    public RestAPIResponse add(CourseDto courseDto) {
        boolean exists = courseRepository.existsByName(courseDto.getName());
        if (exists) {
            return new RestAPIResponse(COURSE + ALREADY_EXIST,false,404);
        }
        CourseEntity courseEntity = modelMapper.map(courseDto, CourseEntity.class);
        courseRepository.save(courseEntity);
        return new RestAPIResponse(SUCCESSFULLY_SAVED,true,200);
    }

    public RestAPIResponse getList(Pageable pageable) {
        Page<CourseEntity> courseEntities = courseRepository.findAll(pageable);
        List<CourseSectionDto> list = courseEntities.getContent().size() > 0 ?
                courseEntities.getContent().stream().map(u -> modelMapper.map(u, CourseSectionDto.class)).toList() :
                new ArrayList<>();
        PageImpl<CourseSectionDto> courseSectionDtos = new PageImpl<>(list, courseEntities.getPageable(), courseEntities.getTotalElements());
        return new RestAPIResponse(COURSE + DATA_LIST,true,200,courseSectionDtos);
    }

    @Override
    public RestAPIResponse get(Long id) {
        Optional<CourseEntity> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isEmpty()) {
            return new RestAPIResponse(COURSE+NOT_FOUND,false,404);
        }
        CourseDto courseDto = modelMapper.map(optionalCourse.get(), CourseDto.class);
        return new RestAPIResponse(COURSE,true,200,courseDto);
    }

    @Override
    public RestAPIResponse delete(Long id) {
        Optional<CourseEntity> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isEmpty()) {
            return new RestAPIResponse(COURSE + NOT_FOUND,false,404);
        }
        CourseEntity courseEntity = optionalCourse.get();
        courseRepository.delete(courseEntity);
        return new RestAPIResponse(SUCCESSFULLY_DELETED,true,200);
    }

    @Override
    public RestAPIResponse edit(Long id, CourseDto courseDto) {
        Optional<CourseEntity> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isEmpty()) {
            return new RestAPIResponse(COURSE + NOT_FOUND,false,404);
        }
        CourseEntity courseEntity = optionalCourse.get();
        modelMapper.map(courseDto,courseEntity);
        courseRepository.save(courseEntity);
        return new RestAPIResponse(SUCCESSFULLY_UPDATED,true,200);
    }

    public RestAPIResponse getByName (String name) {
        Optional<CourseEntity> optionalCourseEntity = courseRepository.findByName(name);
        if (optionalCourseEntity.isEmpty()) {
            return new RestAPIResponse(COURSE + NOT_FOUND,false,404);
        }
        CourseDto courseDto = modelMapper.map(optionalCourseEntity.get(), CourseDto.class);
        return new RestAPIResponse(COURSE,true,200,courseDto);
    }
}