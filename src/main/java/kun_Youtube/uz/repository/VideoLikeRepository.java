package kun_Youtube.uz.repository;

import kun_Youtube.uz.entity.commentLike.VideoLikeEntity;
import kun_Youtube.uz.enumh.LikeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VideoLikeRepository extends JpaRepository<VideoLikeEntity, String> {

    Optional<VideoLikeEntity> findByVideoIdAndProfileId(String videoId, Integer profileId);

    int deleteByVideoIdAndProfileId(String videoId, Integer profileId);

    List<VideoLikeEntity> findByProfileIdOrderByCreatedDateDesc(Integer profileId);

    List<VideoLikeEntity> findByProfileId(Integer profileId);

    /** Like/Dislike count */
    long countByVideoIdAndType(String videoId, LikeType type);
}
