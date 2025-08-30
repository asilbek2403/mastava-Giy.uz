package kun_Youtube.uz.controller;


import kun_Youtube.uz.dtov.videol.VideoTagResponseDto;
import kun_Youtube.uz.dtov.videol.VideoTagReuestDto;
import kun_Youtube.uz.service.VideoTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/video-tag")
public class VideoTagController {

    @Autowired
    private  VideoTagService videoTagService;

    // 1. Videoga tag qo‘shish
    @PostMapping("/add")
    public VideoTagResponseDto addTag(@RequestBody VideoTagReuestDto dto) {
        return videoTagService.addTagToVideo(dto);
    }

    // 2. Videodan tagni olib tashlash
    @DeleteMapping("/remove")
    public String removeTag(@RequestBody VideoTagReuestDto dto) {
        videoTagService.removeTagFromVideo(dto);
        return "Tag removed from video";
    }

    // 3. Video tag list
    @GetMapping("/{videoId}")
    public List<VideoTagResponseDto> getTags(@PathVariable String videoId) {
        return videoTagService.getTagsByVideo(videoId);
    }


}

