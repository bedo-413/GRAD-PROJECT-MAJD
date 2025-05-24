package jordan.university.gradproject2.repository.activitylog;

import jordan.university.gradproject2.entity.ActivityFormLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ActivityFormLogJpaRepository extends JpaRepository<ActivityFormLogEntity, Long>, JpaSpecificationExecutor<ActivityFormLogEntity> {
    List<ActivityFormLogEntity> findByFormUuidOrderByTimestampAsc(String uuid);
}
