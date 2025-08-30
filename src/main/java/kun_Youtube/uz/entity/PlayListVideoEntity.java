package kun_Youtube.uz.entity;

import jakarta.persistence.*;
import kun_Youtube.uz.dtov.videol.VideoEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;



@Getter
@Setter
@Entity
@Table(name = "playlist_video")
public class PlayListVideoEntity {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id", nullable = false)
    private PlayListEntity playlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", nullable = false)
    private VideoEntity video;

    @Column(name = "order_num")
    private Integer orderNum;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    // getter/setter
}

