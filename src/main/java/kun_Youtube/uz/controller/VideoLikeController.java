package kun_Youtube.uz.controller;




import kun_Youtube.uz.dtov.commitLike.VideoLikeInfo;
import kun_Youtube.uz.enumh.LikeType;
import kun_Youtube.uz.service.VideoLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/video-like")
@RequiredArgsConstructor
public class VideoLikeController {


    @Autowired
    private  VideoLikeService videoLikeService;

    /** Like/Dislike qilish */
    @PostMapping("/likeVideo/{videoId}")
    public ResponseEntity<Void> likeVideo(@PathVariable String videoId,
                                          @RequestParam LikeType status) {
        videoLikeService.create(videoId, status);
        return ResponseEntity.ok().build();
    }

    /** Like/Dislike ni olib tashlash */
    @DeleteMapping("/likeVideo/{videoId}")
    public ResponseEntity<Boolean> removeLike(@PathVariable String videoId) {
        return ResponseEntity.ok(videoLikeService.remove(videoId));
    }

    /** O‘ziga yoqqan videolar */
    @GetMapping("/likeVideo/user")
    public ResponseEntity<List<VideoLikeInfo>> getUserLikedVideos() {
        return ResponseEntity.ok(videoLikeService.userLikedVideos());
    }

    /** Boshqa userning yoqqan videolari (faqat ADMIN) */
    @GetMapping("/likeVideo/user/{userId}")
    public ResponseEntity<List<VideoLikeInfo>> getUserLikedVideosById(@PathVariable Integer userId) {
        return ResponseEntity.ok(videoLikeService.getUserLikedVideosByUserId(userId));
    }

    /** Like count va Dislike count */
    @GetMapping("/likeVideoDislike/{videoId}/count")
    public ResponseEntity<?> getLikeDislikeCount(@PathVariable String videoId) {
        return ResponseEntity.ok(
                new LikeCountResponse(
                        videoLikeService.getLikeCount(videoId),
                        videoLikeService.getDislikeCount(videoId)
                )
        );
    }

    /** Foydalanuvchi reaction statusi */
    @GetMapping("/likeVideo/{videoId}/reaction")
    public ResponseEntity<LikeType> getUserReaction(@PathVariable String videoId) {
        return ResponseEntity.ok(videoLikeService.getUserReaction(videoId));
    }

    /** Like/Dislike sonlarini qaytaruvchi DTO */
    public record LikeCountResponse(Long likeCount, Long dislikeCount) {}



}

