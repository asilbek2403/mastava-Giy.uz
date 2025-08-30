package kun_Youtube.uz.dtov.playList;

import kun_Youtube.uz.dtov.videol.VideoDto;
import kun_Youtube.uz.enumh.PlayStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class PlayListShortInfo {
    private String id;
    private String name;
    private LocalDateTime createdDate;

    private ChannelDetailDTO channel;  // ichida channel (id, name)

    private Long videoCount;     // playlistdagi video soni

    private List<VideoDto> videoList;
    /*
     id, name,created_date,channel(id,name),video_count,video_list[{id,name,duration}] (first 2)

     */

    private String description;
    private PlayStatus status;
    private Long orderNum;
}
