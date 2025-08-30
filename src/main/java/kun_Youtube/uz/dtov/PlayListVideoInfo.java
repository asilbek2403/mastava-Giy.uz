package kun_Youtube.uz.dtov;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class PlayListVideoInfo {
    private String playlistId;
//    private VideoShortDto video;
    private ChannelShortDto channel;
    private LocalDateTime createdDate;
    private Integer orderNum;

}
