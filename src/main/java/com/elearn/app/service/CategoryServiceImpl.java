package com.elearn.app.service;

import com.elearn.app.dtos.CategoryDto;
import com.elearn.app.dtos.CourseDto;
import com.elearn.app.dtos.CustomPageResponse;
import com.elearn.app.entities.Category;
import com.elearn.app.entities.Course;
import com.elearn.app.exception.ResourceNotFoundException;
import com.elearn.app.repositories.CategoryRepo;
import com.elearn.app.repositories.CourseRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepo categoryRepo;
    private CourseRepo courseRepo;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepo categoryRepo, CourseRepo courseRepo, ModelMapper modelMapper) {
        this.categoryRepo = categoryRepo;
        this.courseRepo = courseRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto insert(CategoryDto categoryDto) {

        String catId= UUID.randomUUID().toString();
        categoryDto.setId(catId);
        categoryDto.setAddedDate(new Date());
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepo.save(category);


        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CustomPageResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy) {
        //This line is used for sorting
        Sort sort = Sort.by(sortBy).ascending();
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize,sort);

        Page<Category> categoryPage = categoryRepo.findAll(pageRequest);
        List<Category> all = categoryPage.getContent();

        List<CategoryDto> categoryDtoList=all.stream().map(category -> modelMapper.map(category,CategoryDto.class)).toList();
        CustomPageResponse<CategoryDto> customPageResponse = new CustomPageResponse<>();
        customPageResponse.setContent(categoryDtoList);
        customPageResponse.setLast(categoryPage.isLast());
        customPageResponse.setTotalElement(categoryPage.getTotalElements());
        customPageResponse.setPageNumber(pageNumber);
        customPageResponse.setPageSize(categoryPage.getSize());
        customPageResponse.setTotalPages(categoryPage.getTotalPages());

        return customPageResponse;
    }

    @Override
    public CategoryDto get(String categoryId) {
//        System.out.println("Category id is "+categoryId);
       Category category=categoryRepo.findById(categoryId)
               .orElseThrow(()->new ResourceNotFoundException("Category Not found"));
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categoryRepo.delete(category);

    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        category.setTitle(categoryDto.getTitle());
        category.setDesc(categoryDto.getDesc());
        Category savedCategory = categoryRepo.save(category);
        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    @Transactional
    public void addCourseToCategory(String catId, String courseId) {

        Category category = categoryRepo.findById(catId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        category.addCourse(course);
        categoryRepo.save(category);

        System.out.println("Category relationship updated");
    }

    @Override
    @Transactional // to avoid the no session error
    public List<CourseDto> getCoursesOfCat(String categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Courses not found !!"));
        List<Course> courses = category.getCourses();
        return courses.stream().map(course -> modelMapper.map(course,CourseDto.class)).toList();
    }


}
