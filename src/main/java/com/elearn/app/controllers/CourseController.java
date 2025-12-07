package com.elearn.app.controllers;


import com.elearn.app.config.AppConstants;
import com.elearn.app.dtos.CourseDto;
import com.elearn.app.dtos.CustonMessage;
import com.elearn.app.repositories.CourseRepo;
import com.elearn.app.service.CourseService;
import com.elearn.app.service.FileService;
import com.sun.jdi.PrimitiveValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    @Autowired
    private  FileService fileService;

    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    //Creating the new Resource
    @PostMapping
    public ResponseEntity<CourseDto> createCourse(
            @RequestBody CourseDto courseDto
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(courseDto));
    }

    //Updating the Existing Resource
    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourse(
            @RequestBody CourseDto courseDto,@PathVariable String id)
    {
        return ResponseEntity.ok(courseService.updateCourse(courseDto, id));

    }

    //Update using Patch
    @PatchMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourseUsingPatch(
            @RequestBody CourseDto courseDto, @PathVariable String id
    )
    {
       return ResponseEntity.ok(courseService.updateCourseUsingPatch(courseDto,id));
    }

    //get a single course
    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable String id)
    {
        return ResponseEntity.ok(courseService.getCourseByID(id));
    }

    //Get All Courses
    @GetMapping
    public ResponseEntity<Page<CourseDto>> getAllCourse(Pageable pageable)
    {
        return  ResponseEntity.ok(courseService.getAllCourses(pageable));
    }

    //Delete the courses
    @DeleteMapping("/{id}")
    public ResponseEntity<CustonMessage> deleteCourse(@PathVariable String id)
    {
        courseService.deleteCourse(id);
        CustonMessage custonMessage = new CustonMessage();
        custonMessage.setMessage("Courses deleted");
        custonMessage.setSuccess(true);
        return ResponseEntity.status(HttpStatus.OK).body(custonMessage);
    }

    //Search courses
   @GetMapping("/search")
    public ResponseEntity<List<CourseDto>> searchCourse1(
            @RequestParam String Keyword
    )
   {
       return ResponseEntity.ok(courseService.searchCourses(Keyword));
   }

   @PostMapping("/{courseId}/banners")
   public ResponseEntity<CustonMessage> uploadBanner(
           @PathVariable String courseId,
           @RequestParam("banner") MultipartFile banner
   ) throws IOException {

       System.out.println(banner.getOriginalFilename());
       System.out.println(banner.getContentType());
       System.out.println(banner.getName());
       System.out.println(banner.getSize());
       System.out.println(AppConstants.COURSE_BANNER_UPLOAD_DIR);
       fileService.save(banner, AppConstants.COURSE_BANNER_UPLOAD_DIR, banner.getOriginalFilename());
       return ResponseEntity.ok(null);
   }

}
