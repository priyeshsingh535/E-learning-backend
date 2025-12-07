package com.elearn.app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Video {

    @Id
    private String videoId;

    private String title;

    @Column(name = "description", length = 1000)
    private String shortDesc;

//    private String desc;

    private String filePath;

    private String contentType;

    @ManyToOne
    private Course course;
}
