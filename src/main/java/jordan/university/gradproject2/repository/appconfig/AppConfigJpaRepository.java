package jordan.university.gradproject2.repository.appconfig;

import jordan.university.gradproject2.entity.AppConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppConfigJpaRepository extends JpaRepository<AppConfigEntity, Long> {
    Optional<AppConfigEntity> findByKey(String key);
    Optional<AppConfigEntity> findByUuid(String uuid);
    boolean existsByKey(String key);
}
