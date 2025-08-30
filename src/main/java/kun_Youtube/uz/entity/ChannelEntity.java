package kun_Youtube.uz.entity;


import jakarta.persistence.*;
import kun_Youtube.uz.dtov.videol.VideoEntity;
import kun_Youtube.uz.enumh.ChannelStatus;
import lombok.Getter;
import lombok.Setter;
//import org.springframework.context.annotation.Lazy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "channel")
public class ChannelEntity {
    @Id
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name="description")
    private String description;
    @Column(name="photo_id")
    private String photoId;
    @Column(name="banner_id")
    private String bannerId;
//Attachdan keladi idlari
  //    @ManyToOne
    @Column(name="profile_id")
    private Integer profileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false,nullable = false)
    private ProfileEntity profile;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ChannelStatus status=ChannelStatus.ACTIVE;

    @Column(name = "created_date")
    private LocalDateTime createdDate;


    @OneToMany(mappedBy ="channel" )
    private List<PlayListEntity> playlist;
    @ManyToOne
    @JoinColumn(name = "photo", nullable = false)
    private AttachEntity photo;


    @OneToMany(mappedBy = "channel", fetch = FetchType.LAZY)
    private List<VideoEntity> videoList = new ArrayList<>();









}
/*
Channel
    id(uuid),name,photo,banner,description,status (ACTIVE, BLOCK),profile_id
 */