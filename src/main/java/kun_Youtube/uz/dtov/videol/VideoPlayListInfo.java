package kun_Youtube.uz.dtov.videol;


import kun_Youtube.uz.dtov.AttachDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoPlayListInfo {
    private String id;
    private String title;
    private AttachDto previewAttach;
    private Long viewCount;
    private LocalDateTime publishedDate;
    private Long duration;
}

