package kun_Youtube.uz.repository;


import kun_Youtube.uz.entity.PlayListEntity;
import kun_Youtube.uz.enumh.PlayStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayListRepository extends CrudRepository<PlayListEntity, String> {

//    Long findMaxOrderNumByChannelId(String channelId);
    @Query("SELECT MAX(p.orderNum) FROM PlayListEntity p WHERE p.channelId = :channelId")
    Long findMaxOrderNumByChannelId(@Param("channelId") String channelId);

    Iterable<PlayListEntity> findByChannelIdAndDeletedFalseOrderByOrderNumDesc(String channelId);
   //5
     Page<PlayListEntity> findAllByDeletedFalse(Pageable pageable);

    List<PlayListEntity> findByChannelIdAndStatusAndDeletedFalseOrderByOrderNumDesc(
            String channelId, PlayStatus status);

    Iterable<PlayListEntity> findByDeletedFalseOrderByCreatedDateDesc();
    List<PlayListEntity> findByDeletedFalseAndChannel_ProfileIdOrderByOrderNumDesc(Integer profileId);


//    List<PlayListEntity> findByChannelIdOrderByOrderNum(String channelId);
 //delete bo'lmagan activl
//    List<PlayListEntity> findAllByChannelIdAndDeletedFalseOrderByOrderNumDesc(String channelId);

    @Query("SELECT p FROM PlayListEntity p " +
            "JOIN FETCH p.channel c " +    // kanalni ham olib kelamiz
            "WHERE p.id = :playlistId AND p.status = :status")
    Optional<PlayListEntity> findDetailById(@Param("playlistId") String playlistId,
                                            @Param("status") PlayStatus status);


}
