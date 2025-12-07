package com.elearn.app.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    private String id;

    @Column(nullable = true)
    private String title;

    @Column(name = "description")
    private String desc;

    private Date addedDate;

    @ManyToMany(mappedBy = "categoryList", cascade = CascadeType.ALL)
    private List<Course> courses=new ArrayList<>();

    public void addCourse(Course course)
    {
        courses.add(course);
        course.getCategoryList().add(this);
    }
    public void removeCourse(Course course)
    {
        courses.remove(course);
        course.getCategoryList().remove(this);
    }



}
