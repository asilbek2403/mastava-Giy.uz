package kun_Youtube.uz.entity.video;


import jakarta.persistence.*;
import kun_Youtube.uz.dtov.videol.VideoEntity;
import kun_Youtube.uz.entity.TagEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "video_Tag")
public class VideoTagEntity {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", nullable = false)
    private VideoEntity video;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private TagEntity tag;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

}
