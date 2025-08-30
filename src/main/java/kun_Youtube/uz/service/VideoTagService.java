package kun_Youtube.uz.service;


import kun_Youtube.uz.dtov.videol.VideoEntity;
import kun_Youtube.uz.dtov.videol.VideoTagResponseDto;
import kun_Youtube.uz.dtov.videol.VideoTagReuestDto;
import kun_Youtube.uz.entity.TagEntity;
import kun_Youtube.uz.entity.video.VideoTagEntity;
import kun_Youtube.uz.repository.TagRepository;
import kun_Youtube.uz.repository.VideoRepository;
import kun_Youtube.uz.repository.VideoTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class VideoTagService {


    @Autowired
    private VideoTagRepository videoTagRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private VideoRepository videoRepository;

    // 1. Videoga tag qo‘shish
    public VideoTagResponseDto addTagToVideo(VideoTagReuestDto dto) {
        VideoEntity video = videoRepository.findById(dto.getVideoId())
                .orElseThrow(() -> new RuntimeException("Video not found"));

        TagEntity tag = tagRepository.findById(dto.getTagId())
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        // bir xil tag bir xil videoga qo‘shilmasligi uchun check
        videoTagRepository.findByVideo_IdAndTag_Id(dto.getVideoId(), dto.getTagId())
                .ifPresent(vt -> {throw new RuntimeException("Tag already exists for this video");});

        VideoTagEntity entity = new VideoTagEntity();
        entity.setVideo(video);
        entity.setTag(tag);

        videoTagRepository.save(entity);

        VideoTagResponseDto response = new VideoTagResponseDto();
        response.setId(UUID.randomUUID().toString());
        response.setVideoId(video.getId());
        response.setTagId(tag.getId());
        response.setTagName(tag.getName());
        response.setCreatedDate(entity.getCreatedDate());

        return response;
    }

    // 2. Videodan tagni olib tashlash
    public void removeTagFromVideo(VideoTagReuestDto dto) {
        VideoTagEntity entity = videoTagRepository.findByVideo_IdAndTag_Id(dto.getVideoId(), dto.getTagId())
                .orElseThrow(() -> new RuntimeException("Tag not found for this video"));
        videoTagRepository.delete(entity);
    }

    // 3. Videoning barcha taglarini olish
    public List<VideoTagResponseDto> getTagsByVideo(String videoId) {
        List<VideoTagEntity> list = videoTagRepository.findByVideo_Id(videoId);

        List<VideoTagResponseDto> dtoList = new ArrayList<>();

        for (VideoTagEntity entity : list) {
            VideoTagResponseDto dto = new VideoTagResponseDto();
            dto.setId(entity.getId());
            dto.setVideoId(entity.getVideo().getId());
            dto.setTagId(entity.getTag().getId());
            dto.setTagName(entity.getTag().getName());
            dto.setCreatedDate(entity.getCreatedDate());

            dtoList.add(dto);
        }

        return dtoList;
    }



}
