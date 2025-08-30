package kun_Youtube.uz.repository;


import kun_Youtube.uz.entity.PlayListVideoEntity;
import kun_Youtube.uz.enumh.PlayStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayListVideoRepository extends CrudRepository<PlayListVideoEntity,Integer> {
    Optional<PlayListVideoEntity> findByPlaylist_IdAndVideo_Id(String playlistId, String videoId);

    List<PlayListVideoEntity> findByPlaylist_IdAndVideo_StatusOrderByOrderNumAsc(
            String playlistId, PlayStatus status);
    List<PlayListVideoEntity> findByPlaylistId(String playlistId);


}

