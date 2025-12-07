package com.elearn.app.service;


import com.elearn.app.dtos.VideoDto;
import com.elearn.app.entities.Video;
import com.elearn.app.repositories.VideoRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class videoServiceImpl implements VideoService{

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private VideoRepo videoRepo;



    @Override
    public VideoDto createVideo(VideoDto videoDto) {
        String videoId= UUID.randomUUID().toString();
        videoDto.setVideoId(videoId);
        Video video = modelMapper.map(videoDto, Video.class);
        Video savedvideo = videoRepo.save(video);
        return modelMapper.map(savedvideo, VideoDto.class);
    }

    @Override
    public VideoDto getSingleVideo(String Id) {
        Video video = videoRepo.findById(Id).orElseThrow(() -> new RuntimeException("Video not found"));
        return  modelMapper.map(video, VideoDto.class);
    }

    @Override
    public Page<VideoDto> getAllVideo(Pageable pageable) {
        Page<Video> videos = videoRepo.findAll(pageable);
        List<VideoDto> dtos = videos.getContent().stream().map(video -> modelMapper.map(video, VideoDto.class)).toList();
        return  new PageImpl<>(dtos, pageable, videos.getTotalElements());
    }

    @Override
    public VideoDto updateVideo(VideoDto videoDto, String videoId) {
        Video video = videoRepo.findById(videoId).orElseThrow(() -> new RuntimeException("Video not found"));
        modelMapper.map(videoDto, video);
        Video savedVideo = videoRepo.save(video);
        return modelMapper.map(savedVideo, VideoDto.class);
    }

    @Override
    public void deleteVideo(String videoId) {
        videoRepo.deleteById(videoId);

    }

    @Override
    public List<VideoDto> searchVideos(String keyword) {
        List<Video> videos = videoRepo.findByTitleContainingIgnoreCaseOrShortDescContainingIgnoreCase(keyword, keyword);
        return videos.stream()
                .map(video -> modelMapper.map(video, VideoDto.class))
                .collect(Collectors.toList());
    }
}
