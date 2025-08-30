package kun_Youtube.uz.repository;

import jakarta.transaction.Transactional;
import kun_Youtube.uz.dtov.CategoryDto;
import kun_Youtube.uz.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

//category ni topafi name bn
    Optional<CategoryEntity> findByName(String name);

    Optional<CategoryEntity> findByIdAndVisibleIsTrue(Integer id);

    @Transactional
    @Modifying
    @Query("update CategoryEntity set visible = false where id = ?1")
    int updateVisibleById(Integer id);


    @Query("from CategoryEntity where visible = true order by createdDate asc")
    List<CategoryEntity> getAllByNameSorted();

    @Query("from CategoryEntity where visible=true order by createdDate desc ")
    Iterable<CategoryEntity> getAllByCreatedDateDesc();


    Page<CategoryEntity> findAllByOrderByCreatedDateDesc(Pageable pageable);

    List<CategoryEntity> findAllByOrderByCreatedDateDesc();

}
