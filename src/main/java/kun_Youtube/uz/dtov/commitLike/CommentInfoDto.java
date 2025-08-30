package kun_Youtube.uz.dtov.commitLike;

import kun_Youtube.uz.dtov.profile.ProfileDto;
import kun_Youtube.uz.dtov.videol.VideoDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class CommentInfoDto {

    private String id;
    private String content;
    private LocalDateTime createdDate;
    private Long likeCount;
    private Long dislikeCount;

    private ProfileDto profile;
    private VideoDto video;

}
