package com.elearn.app.service;

import com.elearn.app.dtos.CourseDto;
import com.elearn.app.entities.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {

    CourseDto createCourse(CourseDto courseDto);

    CourseDto getCourseByID(String id);

    Page<CourseDto> getAllCourses(Pageable pageable);

    CourseDto updateCourse(CourseDto dto, String courseId);

    CourseDto updateCourseUsingPatch(CourseDto dto, String courseId);

    void deleteCourse(String courseId);

    List<CourseDto> searchCourses(String Keyword);
}
