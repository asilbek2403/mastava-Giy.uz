package kun_Youtube.uz.repository;




import kun_Youtube.uz.entity.SubscriptionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, String> {

    Optional<SubscriptionEntity> findByProfileIdAndChannelId(Integer profileId, String channelId);

//    List<SubscriptionEntity> findByProfileIdAndStatusTrue(String profileId);
//
//    List<SubscriptionEntity> findByProfileId(String profileId);

    List<SubscriptionEntity> findByProfileIdAndStatus(Integer profileId, boolean b);

}

