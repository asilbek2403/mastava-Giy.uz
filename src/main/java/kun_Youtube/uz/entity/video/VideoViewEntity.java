package kun_Youtube.uz.entity.video;


import jakarta.persistence.*;
import kun_Youtube.uz.dtov.videol.VideoEntity;
import kun_Youtube.uz.entity.ProfileEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "video_viewer")
public class VideoViewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", nullable = false)
    private VideoEntity video;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = true) // guest bo‘lsa null
    private ProfileEntity profile;

    @Column(name = "viewed_date", nullable = false)
    private LocalDateTime viewedDate = LocalDateTime.now();


}


