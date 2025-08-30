package kun_Youtube.uz.dtov.commitLike;

import kun_Youtube.uz.dtov.profile.ProfileDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CommentDto {
    private String id;
    private String content;
    private CommentDto reply;  // parent comment ID
    private ProfileDto profile;

    private Long likeCount;
    private Long disLikeCount;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // Nested replies agar kerak bo‘lsa
    private List<CommentDto> replies;
}
