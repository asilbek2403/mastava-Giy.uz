package kun_Youtube.uz.dtov.videol;


import kun_Youtube.uz.dtov.AttachDto;
import kun_Youtube.uz.dtov.channel.ChannelDto;
import kun_Youtube.uz.dtov.playList.ChannelDetailDTO;
import kun_Youtube.uz.dtov.playList.PlayListDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class VideoShortInfo {
    private String id;
    private String title;
    private AttachDto previewAttach;   // (id,url)
    private LocalDateTime publishedDate;
    private ChannelDetailDTO channel;        // (id,name,photo(url))
    private Long viewCount;
    private Long duration;


    private PlayListDto playList;
}

