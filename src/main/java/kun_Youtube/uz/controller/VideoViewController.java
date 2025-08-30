package kun_Youtube.uz.controller;


import kun_Youtube.uz.service.VideoViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/video")
public class VideoViewController {

    @Autowired
    private VideoViewService videoViewService;

    @PostMapping("/{videoId}/view")
    public ResponseEntity<String> addView(@PathVariable String videoId,
                                          @RequestParam(required = false) Integer profileId) {
        videoViewService.saveView(videoId, profileId);
        return ResponseEntity.ok("View saved");
    }

    @GetMapping("/{videoId}/views/count")
    public ResponseEntity<Long> getViewsCount(@PathVariable String videoId) {
        long count = videoViewService.getViewsCount(videoId);
        return ResponseEntity.ok(count);
    }
}



