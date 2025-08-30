package kun_Youtube.uz.controller;


//import jakarta.servlet.http.HttpServletRequest;
import kun_Youtube.uz.dtov.videol.*;
import kun_Youtube.uz.enumh.PlayStatus;
import kun_Youtube.uz.service.VideoService;
//import kun_Youtube.uz.service.VideoViewService;
//import kun_Youtube.uz.util.JWTUtil;
import kun_Youtube.uz.util.SpringSecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/profile-video")
public class VideoController {

    @Autowired
    private VideoService videoService;
//    @Autowired
//    private VideoViewService videoViewService;


    @PostMapping("/create")
    public ResponseEntity<VideoDto> create(@RequestBody VideoCreateDto dto) {
        Integer profileId = SpringSecurityUtil.currentProfileId();
        return ResponseEntity.ok(videoService.create(dto, profileId));
    }


    //update2
    @PutMapping("/update/{id}")
    public ResponseEntity<VideoDto> updateVideoDetail(
            @PathVariable("id") String videoId,
            @RequestBody VideoUpdateDto dto
    ) {
        Integer profileId = SpringSecurityUtil.currentProfileId();
        VideoDto response = videoService.updateVideo(videoId, dto, profileId);
        return ResponseEntity.ok(response);
    }

    // 3. Change Video Status
    @PutMapping("/change-status/{videoId}")
    public ResponseEntity<String> changeStatus(@PathVariable String videoId,
                                               @RequestParam PlayStatus status) {
        Integer profileId = SpringSecurityUtil.currentProfileId(); // token ichidan olish
        videoService.changeVideoStatus(videoId, status, profileId);
        return ResponseEntity.ok("Video status changed successfully!");
    }


    // 4. Increase View Count
    @PostMapping("/increase-view/{videoId}")
    public ResponseEntity<String> increaseView(@PathVariable String videoId) {
        String l =videoService.increaseViewCount(videoId);
        return ResponseEntity.ok(l);

    }

    // 5. Get Video Pagination by Category
    @GetMapping("/category/{categoryId}")
    public Page<VideoShortInfo> getByCategory(@PathVariable Integer categoryId,
                                              Pageable pageable) {
        return videoService.getVideosByCategory(categoryId, pageable);
    }

    // 6. Search Video by Title
    @GetMapping("/titleName/search")
    public Page<VideoShortInfo> search(@RequestParam String title, Pageable pageable) {
        return videoService.searchVideosByTitle(title, pageable);
    }

    // 7. Get Videos by Tag ID
    @GetMapping("/tag/{tagId}")
    public Page<VideoShortInfo> getByTag(@PathVariable String tagId, Pageable pageable) {
        return videoService.getVideosByTag(tagId, pageable);
    }


    @GetMapping("/by-Id-Video/{videoId}")
    public ResponseEntity<VideoFullInfo> getVideoById(
            @PathVariable("videoId") String videoId) {

        // JWT dan profileId ni olish (sizda oldin CustomUserDetails bilan ishlash bor edi)
        Integer profileId = SpringSecurityUtil.currentProfileId();

        VideoFullInfo dto = videoService.getVideoById(videoId, profileId);
        return ResponseEntity.ok(dto);
    }

    // 9. ADMIN: Video List Pagination
    @GetMapping("/admin/pagination")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<VideoShortInfo>> getVideoListForAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<VideoShortInfo> result = videoService.getVideoListForAdmin(pageable);
        return ResponseEntity.ok(result);
    }

    // 10. Channel video list (created_date desc)
    @GetMapping("/channel/{channelId}")
    public ResponseEntity<Page<VideoPlayListInfo>> getChannelVideos(
            @PathVariable String channelId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<VideoPlayListInfo> result = videoService.getChannelVideos(channelId, pageable);
        return ResponseEntity.ok(result);
    }



//    // guest yoki user view qo‘shadi
//    @PostMapping("/{videoId}")
//    public String addView(@PathVariable String videoId,
//                          @RequestParam(required = false) Integer profileId) {
//        videoViewService.saveView(videoId, profileId);
//        return "View added";
//    }
//
//    // videoning umumiy view sonini olish
//    @GetMapping("/{videoId}/count")
//    public long getViewCount(@PathVariable String videoId) {
//        return videoViewService.getViewsCount(videoId);
//    }



}
