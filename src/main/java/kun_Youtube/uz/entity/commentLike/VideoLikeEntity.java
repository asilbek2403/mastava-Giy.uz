package kun_Youtube.uz.entity.commentLike;

import jakarta.persistence.*;
import kun_Youtube.uz.dtov.videol.VideoEntity;
import kun_Youtube.uz.enumh.LikeType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "video_like")
public class VideoLikeEntity {
    @Id
    private String id;

    @Column(nullable = false)
    private Integer profileId;

    @Column(nullable = false)
    private String videoId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "video_id", insertable = false, updatable = false)
//    private VideoEntity video;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LikeType type;  // LIKE, DISLIKE

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}

