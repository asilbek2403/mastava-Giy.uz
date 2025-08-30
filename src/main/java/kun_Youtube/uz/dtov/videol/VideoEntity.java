package kun_Youtube.uz.dtov.videol;


import jakarta.persistence.*;
import kun_Youtube.uz.entity.*;
//import kun_Youtube.uz.entity.video.VideoViewEntity;
import kun_Youtube.uz.entity.commentLike.CommentEntity;
import kun_Youtube.uz.enumh.PlayStatus;
import kun_Youtube.uz.enumh.VideoEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "video")
public class VideoEntity {
    @Id
    private String id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @Column
    private Long duration; // soniyalarda masalan: 180 (3 daqiqa)


    @Column
    @Enumerated(EnumType.STRING)
    private VideoEnum videoType = VideoEnum.VIDEO;

    @Column(name = "view_count")
    private Long viewCount = 0L;
    private Long sharedCount = 0L;

    private Long likeCount = 0L;
    private Long dislikeCount = 0L;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PlayStatus status ;

    @Column(columnDefinition = "text")
    private String description;


    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime publishedDate;

    // RELATIONS
    @ManyToMany
    @JoinTable(
            name = "video_tag", // join table
            joinColumns = @JoinColumn(name = "video_id"), // video taraf
            inverseJoinColumns = @JoinColumn(name = "tag_id") // tag taraf
    )
    private List<TagEntity> tagList;




    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    private PlayListEntity playlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", nullable = false)
    private ChannelEntity channel;




    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id", nullable = false)
    private AttachEntity attach;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preview_attach_id")
    private AttachEntity previewAttach;



//    @OneToMany(mappedBy = "video", fetch = FetchType.LAZY)
//    private List<VideoViewEntity> views = new ArrayList<>();



    @OneToMany(mappedBy = "video")
    private List<CommentEntity> commentList;



}
/*
 id(uuid), preview_attach_id,title,category_id,attach_id,created_date,published_date,
      status(private,public),
     type(video,short),view_count,shared_count,description,channel_id,(like_count,dislike_count),

     view_count -> Okala view_count buyerda ham bo'lsin. Alohida Table ham bo'lsin.
     category_id -> bitta video bitta category bo'lsin.
 */
