package kun_Youtube.uz.repository;


import kun_Youtube.uz.entity.commentLike.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity, String > {

    Page<CommentEntity> findAll(Pageable pageable);  // ADMIN uchun

    Page<CommentEntity> findByProfileId(Integer profileId, Pageable pageable);

    List<CommentEntity> findByVideoId(String videoId);

    List<CommentEntity> findByReplyId(String commentId);



    // Videodagi kommentlarni olish (faqat ko‘rinadiganlarini)
    @Query("select c from CommentEntity c " +
            " where c.video.id = ?1  " +
            " order by c.createdDate desc")
    Page<CommentEntity> findByVideoId(String videoId, Pageable pageable);

    // Commentning replylarini olish
    @Query("select c from CommentEntity c " +
            " where c.reply.id = ?1 " +
            " order by c.createdDate asc")
    List<CommentEntity> findReplies(String commentId);

}
