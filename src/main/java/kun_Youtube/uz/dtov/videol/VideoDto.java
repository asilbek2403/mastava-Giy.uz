package kun_Youtube.uz.dtov.videol;


import kun_Youtube.uz.enumh.PlayStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class VideoDto {
    private String id;
    private String title;
    private String description;

    private String previewAttachId;
    private String attachId;

    private Integer categoryId;
    private String channelId;

    private List<Integer> tagList;

    private PlayStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private Long duration;

    private Long viewCount;
    private Long likeCount;
    private Long dislikeCount;
}
