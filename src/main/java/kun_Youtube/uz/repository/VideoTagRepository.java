package kun_Youtube.uz.repository;

import kun_Youtube.uz.entity.video.VideoTagEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoTagRepository extends CrudRepository<VideoTagEntity, String> {

        Optional<VideoTagEntity> findByVideo_IdAndTag_Id(String videoId, Integer tagId);

        List<VideoTagEntity> findByVideo_Id(String videoId);

}
