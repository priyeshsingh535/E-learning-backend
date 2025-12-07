package com.elearn.app.controllers;


import com.elearn.app.config.AppConstants;
import com.elearn.app.dtos.CategoryDto;
import com.elearn.app.dtos.CourseDto;
import com.elearn.app.dtos.CustomPageResponse;
import com.elearn.app.dtos.CustonMessage;
import com.elearn.app.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    //Create category

    @PostMapping
    public ResponseEntity<?> create(
            @Valid  @RequestBody CategoryDto categoryDto
//            BindingResult result
    )
    {
//        if(result.hasErrors())
//        {
//
//           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input");
//        }
        CategoryDto createdDto = categoryService.insert(categoryDto);
        return ResponseEntity.
                status(HttpStatus.CREATED)
                .body(createdDto);

    }


    //Category Get All
    @GetMapping
   public CustomPageResponse getAll(
            @RequestParam(value = "pageNumber", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = AppConstants.DEFAULT_SORT_BY) String sortBy
    )
    {
        return categoryService.getAll(pageNumber,pageSize,sortBy);
    }

    //category Single Get
    @GetMapping("/{categoryId}")
    public CategoryDto getSingle(
            @PathVariable String categoryId
    )
    {
        CategoryDto categoryDto = categoryService.get(categoryId);
        return categoryDto;
    }


    //category delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<CustonMessage> delete(
            @PathVariable String categoryId
    )
    {
        categoryService.delete(categoryId);
        CustonMessage custonMessage = new CustonMessage();
        custonMessage.setMessage("Category deleted");
        custonMessage.setSuccess(true);
        return ResponseEntity.status(HttpStatus.OK).body(custonMessage);
    }

    //Category update
    @PutMapping("/{categoryId}")
    public CategoryDto update(
            @PathVariable String  categoryId,
            @RequestBody CategoryDto categoryDto
    )
    {
        return categoryService.update(categoryDto,categoryId);
    }

    //controller for nested resource
    @PostMapping("/{categoryId}/courses/{courseId}")
    public ResponseEntity<CustonMessage> addCourseToCategory(
            @PathVariable String categoryId,
            @PathVariable String courseId
    )
    {
        CustonMessage custonMessage = new CustonMessage();
        custonMessage.setMessage("Category updated successfully");
        custonMessage.setSuccess(true);
        return ResponseEntity.ok(custonMessage);

    }
    //Api for get all courses for a particular category
    @GetMapping("/{categoryId}/courses")
    public ResponseEntity<List<CourseDto>> getCoursesOfCategory(
            @PathVariable String categoryId
    )
    {
        return ResponseEntity.ok(categoryService.getCoursesOfCat(categoryId));
    }
}
