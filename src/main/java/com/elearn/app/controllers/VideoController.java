package com.elearn.app.controllers;


import com.elearn.app.dtos.VideoDto;
import com.elearn.app.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {


    private VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    //Creating new Resource
    @PostMapping
    public ResponseEntity<VideoDto> createVideo(
            @RequestBody VideoDto videoDto
    )
    {
        return ResponseEntity.ok(videoService.createVideo(videoDto));
    }
    //Updating new Resource
    @PutMapping("/{id}")
    public ResponseEntity<VideoDto> updateVideo(
            @RequestBody VideoDto videoDto, @PathVariable String id)
    {
        return ResponseEntity.ok(videoService.updateVideo(videoDto,id));
    }

    //
    @GetMapping("/{id}")
    public ResponseEntity<VideoDto> getSingleVideo(
            @PathVariable String id
    )
    {
        return ResponseEntity.ok(videoService.getSingleVideo(id));
    }

    //Get All videos
    @GetMapping
    public ResponseEntity<Page<VideoDto>> getAllvideos(Pageable pageable)
    {
        return ResponseEntity.ok(videoService.getAllVideo(pageable));
    }
    //
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(
            @PathVariable String id
    )
    {
     videoService.deleteVideo(id);
     return ResponseEntity.noContent().build();
    }
    //Search by title
    @GetMapping("/search")
    public ResponseEntity<List<VideoDto>> searchVideo(
            @RequestParam String keyword
    )
    {
        return ResponseEntity.ok(videoService.searchVideos(keyword));
    }
}
