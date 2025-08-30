package kun_Youtube.uz.dtov.playList;

import kun_Youtube.uz.enumh.PlayStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PlayListInfo {

    private String id;
    private String name;
    private String description;

    private PlayStatus status;   // PUBLIC / PRIVATE

    private Long orderNum;

    private ChannelDetailDTO channel;
    /*
     id,name,description,status(private,public),order_num,
            channel(id,name,photo(id,url), profile(id,name,surname,photo(id,url)))

     */
//    private Long OrderNum;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdateDate;
}
