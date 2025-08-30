package kun_Youtube.uz.repository;

import kun_Youtube.uz.entity.ProfileEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {

    Optional<ProfileEntity> findByIdAndVisibleIsTrue(Integer id);

//    Optional<ProfileEntity> findByPhoneAndVisibleTrue(String phone);

    Optional<ProfileEntity> findByUsernameAndVisibleTrue(String username);

    Page<ProfileEntity> findAllByVisibleTrue(Pageable pageable);

    Optional<ProfileEntity> findByUsernameAndVisibleIsTrue(String username);

}
