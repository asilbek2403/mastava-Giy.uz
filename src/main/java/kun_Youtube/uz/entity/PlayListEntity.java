package kun_Youtube.uz.entity;


import jakarta.persistence.*;
import kun_Youtube.uz.dtov.videol.VideoEntity;
import kun_Youtube.uz.enumh.PlayStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "play_list")
public class PlayListEntity {

    @Id
    private String id;

    @Column
    private String name;

    @Column(length = 1000)//(100)
    private String description;

        @Column(name = "channel_id")
        private String channelId;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "channel_id" ,insertable = false, updatable = false,nullable = false)
        private ChannelEntity channel;


    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PlayStatus status=PlayStatus.PUBLIC;

    @Column(name="order_num")
    private Long orderNum;



    @Column(name = "created_Date")
    private LocalDateTime createdDate;
    @Column
    private LocalDateTime lastUpdateDate;   // oxirgi update sanasi



    @Column(name = "deleted")
    private Boolean deleted = false;



    // RELATIONS


    @OneToMany(mappedBy = "playlist", fetch = FetchType.LAZY)
    private List<VideoEntity> videos;

}


/*
    id,channel_id,name,description,status(private,public),order_num

 */