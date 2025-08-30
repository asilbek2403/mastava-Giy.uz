package kun_Youtube.uz.repository;

import kun_Youtube.uz.dtov.channel.ChannelDto;
import kun_Youtube.uz.entity.ChannelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelRepository extends CrudRepository<ChannelEntity,Integer> {


    Optional<ChannelEntity> findById(String channelId);

    @Query(
            value = "SELECT c FROM ChannelEntity c WHERE c.status = 'ACTIVE'",
            countQuery = "SELECT count(c) FROM ChannelEntity c WHERE c.status = 'ACTIVE'"
    )
    Page<ChannelEntity> findAllActive(Pageable pageable);


    //User channelList
    @Query("from ChannelEntity c where c.profileId = :profileId")
    Page<ChannelEntity> findAllByProfileId(@Param("profileId") Integer profileId, Pageable pageable);

    List<ChannelEntity> findAllByProfileId(Integer profileId);

    @Query("from ChannelEntity c where c.profileId = :profileId")
    Optional<ChannelEntity> findByProfileId(Integer profileId);

}
