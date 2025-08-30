package kun_Youtube.uz.dtov.commitLike;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateDto {
    private String content;
    private String replyId;  // optional
}
