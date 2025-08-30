package kun_Youtube.uz.entity.commentLike;


import jakarta.persistence.*;
import kun_Youtube.uz.dtov.videol.VideoEntity;
import kun_Youtube.uz.entity.ProfileEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "comment")
public class CommentEntity {

    @Id
    private String id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private ProfileEntity profile;   // kim yozgan

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    private VideoEntity video;       // qaysi video ostida

    @ManyToOne
    @JoinColumn(name = "reply_id")
    private CommentEntity reply;     // commentga yozilgan comment

    @OneToMany(mappedBy = "reply", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> replies;

    @Column(name = "like_count")
    private Long likeCount = 0L;

    @Column(name = "dislike_count")
    private Long dislikeCount = 0L;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

}

