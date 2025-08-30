package kun_Youtube.uz.repository;


import kun_Youtube.uz.entity.AttachEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachRepository extends CrudRepository<AttachEntity,String> {

    @Query("from AttachEntity where visible = true order by createdDate desc")
    Page<AttachEntity> findAllByOrderByCreatedDateDesc(Pageable pageable);


}
