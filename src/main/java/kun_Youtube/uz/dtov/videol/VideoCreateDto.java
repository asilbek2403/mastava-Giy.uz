package kun_Youtube.uz.dtov.videol;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VideoCreateDto {

    private String title;
    private String description;
    private String previewAttachId;
    private String attachId;
    private Integer categoryId;
    private List<Integer> tagList;
    private Long duration;
    private String channelId;


}
