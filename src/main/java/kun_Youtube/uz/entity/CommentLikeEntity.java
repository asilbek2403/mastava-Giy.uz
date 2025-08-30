package kun_Youtube.uz.entity;

import jakarta.persistence.*;
import kun_Youtube.uz.enumh.LikeType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comment_like")
public class CommentLikeEntity {

    @Id
    private String id;

    @Column(name = "profile_id", nullable = false)
    private Integer profileId;

    @Column(name = "comment_id", nullable = false)
    private String commentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LikeType type;   // LIKE yoki DISLIKE

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;


}
