package kun_Youtube.uz.dtov.videol;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoTagResponseDto {

    private String id;
    private String videoId;
    private Integer tagId;
    private String tagName;
    private LocalDateTime createdDate;

}
