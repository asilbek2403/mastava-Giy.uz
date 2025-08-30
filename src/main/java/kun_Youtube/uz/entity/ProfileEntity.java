package kun_Youtube.uz.entity;


import jakarta.persistence.*;
import kun_Youtube.uz.dtov.AttachDto;
import kun_Youtube.uz.entity.commentLike.CommentEntity;
import kun_Youtube.uz.enumh.ProfileRoleEnum;
import kun_Youtube.uz.enumh.ProfileStatusEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="profile")
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String username;
    @Column
    private Boolean visible=Boolean.TRUE;
    @Column
    private String password;
    @ManyToOne
    @JoinColumn(name = "photo_id",insertable = false,updatable = false)
    private AttachEntity photo;
    @Column(name = "photo")
    private String photoId;

    @OneToMany(mappedBy = "profile") //  to‘g‘risi shu
    private List<ProfileRoleEntity> roleList;

    @Column
    @Enumerated(EnumType.STRING)
    private ProfileStatusEnum status=ProfileStatusEnum.ACTIVE;

    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdDate;

    // Channel
    @OneToMany(mappedBy = "profile")
    private List<ChannelEntity> channel;




    @OneToMany(mappedBy = "profile")
    private List<CommentEntity> commentList;


}
/*
Profile{
    id,name,surname,email,password,photo,role,status}
 */
