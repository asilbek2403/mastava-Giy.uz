package kun_Youtube.uz.dtov.commitLike;

import kun_Youtube.uz.dtov.videol.VideoShortInfo;
import kun_Youtube.uz.enumh.LikeType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class VideoLikeInfo {
        private String id;
        private VideoShortInfo video;
        private LikeType type;
        private LocalDateTime createdDate;
    }

