package kun_Youtube.uz.repository;

import kun_Youtube.uz.dtov.videol.VideoEntity;
import kun_Youtube.uz.enumh.PlayStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends CrudRepository<VideoEntity, String> {
    List<VideoEntity> findTop2ByPlaylistIdOrderByCreatedDateAsc(String playlistId);

    Long countByPlaylistId(String playlistId);

    Page<VideoEntity> findByCategoryIdAndStatus(Integer categoryId, PlayStatus status, Pageable pageable);

    Page<VideoEntity> findByTitleContainingIgnoreCaseAndStatus(String title, PlayStatus status, Pageable pageable);

    Page<VideoEntity> findByTagListIdAndStatus(String tagId, PlayStatus status, Pageable pageable);

    //9-10
    Page<VideoEntity> findAll(Pageable pageable);

    // 10. Channel videolari (created_date desc tartibda)
    Page<VideoEntity> findByChannelIdOrderByCreatedDateDesc(String channelId, Pageable pageable);






    @Modifying
    @Query("UPDATE VideoEntity v SET v.viewCount = v.viewCount + 1 WHERE v.id = :videoId")
    void incrementViewCount(@Param("videoId") String videoId);


}
