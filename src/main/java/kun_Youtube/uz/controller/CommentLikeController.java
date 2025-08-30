package kun_Youtube.uz.controller;


import kun_Youtube.uz.dtov.CommentLikeInfo;
import kun_Youtube.uz.entity.commentLike.LikeCountResponse;
import kun_Youtube.uz.enumh.LikeType;
import kun_Youtube.uz.service.CommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/comment-like")
public class  CommentLikeController{

    @Autowired
    private CommentLikeService commentLikeService;

    /** Like/Dislike qilish */
    @PostMapping("/commentLike/{commentId}")
    public ResponseEntity<Void> likeComment(@PathVariable String commentId,
                                            @RequestParam LikeType type) {
        commentLikeService.create(commentId, type);
        return ResponseEntity.ok().build();
    }

    /** Like/Dislike ni olib tashlash */
    @DeleteMapping("/commentLike-delete/{commentId}")
    public ResponseEntity<Boolean> removeLike(@PathVariable String commentId) {
        return ResponseEntity.ok(commentLikeService.remove(commentId));
    }

    /** O‘ziga yoqqan kommentlar */
    @GetMapping("/commentLike/user")
    public ResponseEntity<List<CommentLikeInfo>> getUserLikedComments() {
        return ResponseEntity.ok(commentLikeService.userLikedComments());
    }

    /** Boshqa userning yoqqan kommentlari (faqat ADMIN) */
    @GetMapping("/commentLike/user/{userId}")
    public ResponseEntity<List<CommentLikeInfo>> getUserLikedCommentsById(@PathVariable Integer userId) {
        return ResponseEntity.ok(commentLikeService.getUserLikedCommentsByUserId(userId));
    }

    /** Like count va Dislike count */
    @GetMapping("/commentLikeDislike/{commentId}/count")
    public ResponseEntity<LikeCountResponse> getLikeDislikeCount(@PathVariable String commentId) {
        LikeCountResponse dto = new LikeCountResponse();
        dto.setLikeCount(commentLikeService.getLikeCount(commentId));
        dto.setDislikeCount(commentLikeService.getDislikeCount(commentId));

        return ResponseEntity.ok(dto);
    }


    /** Foydalanuvchi reaction statusi */
    @GetMapping("/commentLike/{commentId}/reaction")
    public ResponseEntity<LikeType> getUserReaction(@PathVariable String commentId) {
        return ResponseEntity.ok(commentLikeService.getUserReaction(commentId));
    }

    /** Like/Dislike sonlarini qaytaruvchi DTO */

}

