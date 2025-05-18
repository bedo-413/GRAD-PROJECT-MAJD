package jordan.university.gradproject2.repository.activity;

import jordan.university.gradproject2.entity.ActivityFormEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ActivityFormJpaRepository extends JpaRepository<ActivityFormEntity, Long>, JpaSpecificationExecutor<ActivityFormEntity> {

    Optional<ActivityFormEntity> findByUuid(String uuid);

    Optional<ActivityFormEntity> deleteByUuid(String uuid);


}