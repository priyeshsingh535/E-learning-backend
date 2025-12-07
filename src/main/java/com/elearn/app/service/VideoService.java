package com.elearn.app.service;

import com.elearn.app.dtos.VideoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VideoService {

    VideoDto createVideo(VideoDto videoDto);

    VideoDto getSingleVideo(String Id);

    Page<VideoDto> getAllVideo(Pageable pageable);

    VideoDto updateVideo(VideoDto videoDto, String videoId);

    void deleteVideo(String videoId);

    List<VideoDto> searchVideos(String keyword);

}
