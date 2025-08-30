package kun_Youtube.uz.repository;

import kun_Youtube.uz.entity.CommentLikeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity, Long> {

    Optional<CommentLikeEntity> findByCommentIdAndProfileId(String commentId, Integer profileId);

    boolean existsByCommentIdAndProfileId(String commentId, Integer profileId);

    void deleteByCommentIdAndProfileId(String commentId, Integer profileId);

    @Query("SELECT c FROM CommentLikeEntity c WHERE c.profileId = ?1 ORDER BY c.createdDate DESC")
    List<CommentLikeEntity> findByProfileIdOrderByCreatedDateDesc(Integer profileId);

    @Query("SELECT COUNT(c) FROM CommentLikeEntity c WHERE c.commentId = ?1 AND c.type = 'LIKE'")
    Long countLikeByCommentId(String commentId);

    @Query("SELECT COUNT(c) FROM CommentLikeEntity c WHERE c.commentId = ?1 AND c.type = 'DISLIKE'")
    Long countDislikeByCommentId(String commentId);
}
