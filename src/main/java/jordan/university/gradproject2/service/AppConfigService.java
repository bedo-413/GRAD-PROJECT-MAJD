package jordan.university.gradproject2.service;

import jakarta.transaction.Transactional;
import jordan.university.gradproject2.mapper.AppConfigMapper;
import jordan.university.gradproject2.model.AppConfig;
import jordan.university.gradproject2.repository.appconfig.AppConfigRepository;
import jordan.university.gradproject2.resource.AppConfigResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

public class AppConfigService {

    private final AppConfigRepository appConfigRepository;
    private final AppConfigMapper appConfigMapper;

    public AppConfigService(AppConfigRepository appConfigRepository, AppConfigMapper appConfigMapper) {
        this.appConfigRepository = appConfigRepository;
        this.appConfigMapper = appConfigMapper;
    }

    @Transactional
    public List<AppConfigResource> findAll() {
        return appConfigRepository.findAll().stream()
                .map(appConfigMapper::toResource)
                .collect(Collectors.toList());
    }

    @Transactional
    public AppConfigResource findByKey(String key) {
        return appConfigRepository.findByKey(key)
                .map(appConfigMapper::toResource)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Configuration with key " + key + " not found"));
    }

    @Transactional
    public AppConfigResource save(AppConfig appConfig) {
        return appConfigMapper.toResource(appConfigRepository.save(appConfig));
    }

    @Transactional
    public void deleteByKey(String key) {
        if (!appConfigRepository.existsByKey(key)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Configuration with key " + key + " not found");
        }
        appConfigRepository.deleteByKey(key);
    }

    @Transactional
    public boolean existsByKey(String key) {
        return appConfigRepository.existsByKey(key);
    }
}