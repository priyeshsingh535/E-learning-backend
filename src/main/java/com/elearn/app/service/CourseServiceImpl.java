package com.elearn.app.service;

import com.elearn.app.dtos.CourseDto;
import com.elearn.app.entities.Course;
import com.elearn.app.exception.ResourceNotFoundException;
import com.elearn.app.repositories.CourseRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    private CourseRepo courseRepo;

    private ModelMapper modelMapper;


    public CourseServiceImpl(CourseRepo courseRepo, ModelMapper modelMapper) {
        this.courseRepo = courseRepo;
        this.modelMapper = modelMapper;
    }

//    @Override
//    public CourseDto create(CourseDto courseDto) {
//        Course savedCourse = courseRepo.save(this.dtoToEntity(courseDto));
//        return entityToDto(savedCourse);
//    }
//
//    @Override
//    public List<CourseDto> getAll() {
//        List<Course> courses = courseRepo.findAll();
//        List<CourseDto> courseDtoList = courses.stream()
//                .map(course -> entityToDto(course))
//                .collect(Collectors.toList());
//        return courseDtoList;
//
//    }
//
////    @Override
////    public List<CourseDto> getAll() {
//////        List<Course> courses = courseRepo.findAll();
//////        List<CourseDto> courseDtoList = courses.stream().map(course -> entityToDto(course)).collect(Collectors.toList());
//////         return courseDtoList;
////        return null;
////    }


    @Override
    public CourseDto createCourse(CourseDto courseDto) {
        String courseId= UUID.randomUUID().toString();
        courseDto.setId(courseId);
        courseDto.setCreateDate(new Date());
        Course course = modelMapper.map(courseDto, Course.class);
        Course savedCourses = courseRepo.save(course);
        return modelMapper.map(savedCourses, CourseDto.class);
    }

    @Override
    public CourseDto getCourseByID(String id) {
        Course course = courseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return modelMapper.map(course, CourseDto.class);
    }

    @Override
    public Page<CourseDto> getAllCourses(Pageable pageable) {
        Page<Course> courses = courseRepo.findAll(pageable);
        List<CourseDto> dtos = courses.getContent().stream()
                .map(course -> modelMapper.map(course, CourseDto.class)).toList();
        return new PageImpl<>(dtos, pageable, courses.getTotalElements());
    }

    @Override
    public CourseDto updateCourse(CourseDto dto, String courseId) {
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        modelMapper.map(dto, course);
        Course savedCourse = courseRepo.save(course);
        return modelMapper.map(savedCourse, CourseDto.class);
    }

    @Override
    public CourseDto updateCourseUsingPatch(CourseDto dto, String courseId) {
        Course existingCourse = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        modelMapper.map(dto, existingCourse);
        Course updatedCourse = courseRepo.save(existingCourse);
        return modelMapper.map(updatedCourse, CourseDto.class);
    }


    @Override
    public void deleteCourse(String courseId) {
       courseRepo.deleteById(courseId);
    }

    @Override
    public List<CourseDto> searchCourses(String keyword) {
        List<Course> courses = courseRepo.findByTitleContainingIgnoreCaseOrShortDescContainingIgnoreCase(keyword, keyword);
        return courses.stream()
                .map(course -> modelMapper.map(course, CourseDto.class))
                .collect(Collectors.toList());
    }


    //Mapping kr rhe entity ko dto se
    public CourseDto entityToDto(Course course)
    {
//        CourseDto courseDto = new CourseDto();
//        courseDto.setId(course.getId());
//        courseDto.setTitle(course.getTitle());
//        courseDto.setShortDesc(course.getShortDesc());
//        courseDto.setLongDesc(course.getLongDesc());
        CourseDto courseDto = modelMapper.map(course, CourseDto.class);

        return  courseDto;
    }

    public Course dtoToEntity(CourseDto dto)
    {
//        Course course = new Course();
//        course.setId(dto.getId());
//        course.setTitle(dto.getTitle());
        Course course = modelMapper.map(dto, Course.class);
        return  course;
    }
}
