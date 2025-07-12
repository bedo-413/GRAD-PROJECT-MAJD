package jordan.university.gradproject2.repository.appconfig;

import jordan.university.gradproject2.entity.AppConfigEntity;
import jordan.university.gradproject2.mapper.AppConfigMapper;
import jordan.university.gradproject2.model.AppConfig;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AppConfigRepositoryImpl implements AppConfigRepository {

    private final AppConfigJpaRepository appConfigJpaRepository;
    private final AppConfigMapper appConfigMapper;

    public AppConfigRepositoryImpl(AppConfigJpaRepository appConfigJpaRepository, AppConfigMapper appConfigMapper) {
        this.appConfigJpaRepository = appConfigJpaRepository;
        this.appConfigMapper = appConfigMapper;
    }

    @Override
    public List<AppConfig> findAll() {
        return appConfigJpaRepository.findAll().stream()
                .map(appConfigMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AppConfig> findByKey(String key) {
        return appConfigJpaRepository.findByKey(key)
                .map(appConfigMapper::toModel);
    }

    @Override
    public AppConfig save(AppConfig appConfig) {
        AppConfigEntity entity = appConfigMapper.toEntity(appConfig);
        entity = appConfigJpaRepository.save(entity);
        return appConfigMapper.toModel(entity);
    }

    @Override
    public void deleteByKey(String key) {
        appConfigJpaRepository.findByKey(key)
                .ifPresent(appConfigJpaRepository::delete);
    }

    @Override
    public boolean existsByKey(String key) {
        return appConfigJpaRepository.existsByKey(key);
    }
}