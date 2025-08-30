package kun_Youtube.uz.repository;


import jakarta.transaction.Transactional;
import kun_Youtube.uz.entity.TagEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends CrudRepository<TagEntity,Integer> {

    Optional<TagEntity> findByOrderNumber(Integer orderNumber);


    Optional<TagEntity> findByIdAndVisibleIsTrue(Integer id);


    @Transactional
    @Modifying
    @Query("update TagEntity set visible = false where id = ?1")
    int updateVisibleById(Integer id);


    @Query(value = "from TagEntity where visible = true order by orderNumber")
    List<TagEntity> getAllByOrderSorted();



    boolean existsByName(String name);


}
