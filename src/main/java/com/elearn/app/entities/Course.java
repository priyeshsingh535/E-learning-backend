package com.elearn.app.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Courses")
@Data
public class Course {

    @Id
    private String id;

    private String title;

    private String shortDesc;

    @Column(length = 2000)
    private String longDesc;

    private boolean live=false;

    private double discount;

    private double price;

    private Date createDate;

    //
    private String banner;

    @OneToMany(mappedBy = "course")
    private List<Video> videos=new ArrayList<>();


    @ManyToMany
    private List<Category> categoryList=new ArrayList<>();

    //Courses k andar category add kr rhe hein
    public void addCategory(Category category)
    {
        categoryList.add(category);
        category.getCourses().add(this);
    }

    public void removeCategory(Category category)
    {
        categoryList.remove(category);
        category.getCourses().remove(this);
    }




}
