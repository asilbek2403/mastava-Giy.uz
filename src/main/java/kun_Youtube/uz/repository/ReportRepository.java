package kun_Youtube.uz.repository;

import kun_Youtube.uz.entity.ReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends CrudRepository<ReportEntity, String> {
        Page<ReportEntity> findAll(Pageable pageable);

        List<ReportEntity> findByProfileId(Integer profileId);

//        List<ReportEntity> findByProfileId(Integer profileId);



}
