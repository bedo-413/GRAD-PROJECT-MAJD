package jordan.university.gradproject2.repository.appconfig;

import jordan.university.gradproject2.model.AppConfig;

import java.util.List;
import java.util.Optional;

public interface AppConfigRepository {
    List<AppConfig> findAll();
    Optional<AppConfig> findByKey(String key);
    Optional<AppConfig> findByUuid(String uuid);
    AppConfig save(AppConfig appConfig);
    void deleteByKey(String key);
    boolean existsByKey(String key);
}
