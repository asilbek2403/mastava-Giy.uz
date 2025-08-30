package kun_Youtube.uz.dtov.videol;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import kun_Youtube.uz.dtov.AttachDto;
import kun_Youtube.uz.dtov.CategoryDto;
import kun_Youtube.uz.dtov.channel.ChannelDto;
import kun_Youtube.uz.dtov.tag.TagDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class VideoFullInfo {
    private String id;
    private String title;
    private String description;
    private AttachDto previewAttach;   // (id,url)
    private AttachDto attach;          // (id,url,duration)
    private CategoryDto category;      // (id,name)
    private List<TagDto> tagList;      // (id,name)
    private LocalDateTime publishedDate;
    private ChannelDto channel;        // (id,name,photo(url))
    private Long viewCount;
    private Long sharedCount;
    private LikeInfo like;
    private Long duration;
    // (like_count,dislike_count,isUserLiked,isUserDisliked)
}
