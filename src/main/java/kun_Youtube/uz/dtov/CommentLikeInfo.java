package kun_Youtube.uz.dtov;

import kun_Youtube.uz.enumh.LikeType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentLikeInfo {

    private String id;
    private Integer profileId;
    private String commentId;
    private LocalDateTime createdDate;
    private LikeType type;

}
