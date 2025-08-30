package kun_Youtube.uz.repository;


import jakarta.transaction.Transactional;
import kun_Youtube.uz.entity.ProfileRoleEntity;
import kun_Youtube.uz.enumh.ProfileRoleEnum;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRoleRepository extends CrudRepository<ProfileRoleEntity, Integer> {

//    @Query("select roles from ProfileRoleEntity where profileId =?1")
//    List<ProfileRoleEnum> getRoleListByProfileId(Integer profileId); bir xil >>>

    @Query("SELECT p.roles FROM ProfileRoleEntity p WHERE p.profileId = ?1")
    List<ProfileRoleEnum> getRoleListByProfileId(Integer profileId);

    @Transactional
    @Modifying
    @Query("Delete from ProfileRoleEntity where profileId =?1 and roles =?2")
    void deleteByIdAndRoleEnum(Integer profileId, ProfileRoleEnum role);

    @Transactional
    @Modifying
    @Query("Delete from ProfileRoleEntity where profileId =?1")
    void deleteByProfileId(Integer profileId);

}
