package kun_Youtube.uz.repository;

import kun_Youtube.uz.dtov.videol.VideoEntity;
import kun_Youtube.uz.entity.ProfileEntity;
import kun_Youtube.uz.entity.video.VideoViewEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoViewRepository extends CrudRepository<VideoViewEntity, String> {

    boolean existsByVideoAndProfile(VideoEntity video, ProfileEntity profile);


    long countByVideo_Id(String videoId);


}

