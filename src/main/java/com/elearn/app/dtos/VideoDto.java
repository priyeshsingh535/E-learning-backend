package com.elearn.app.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoDto {

    private String videoId;

    private String title;

//    private String desc;

    private String shortDesc;

    private String filePath;

    private String contentType;
}
