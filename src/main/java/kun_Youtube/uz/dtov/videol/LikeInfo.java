package kun_Youtube.uz.dtov.videol;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeInfo {
    private Long likeCount;        // umumiy like soni
    private Long dislikeCount;     // umumiy dislike soni
    private Long isUserLiked;   // hozirgi user like bosganmi
    private Long isUserDisliked;// hozirgi user dislike bosganmi
}
