package jordan.university.gradproject2.controller;

import jordan.university.gradproject2.mapper.AppConfigMapper;
import jordan.university.gradproject2.model.AppConfig;
import jordan.university.gradproject2.request.AppConfigRequest;
import jordan.university.gradproject2.resource.AppConfigResource;
import jordan.university.gradproject2.service.AppConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static jordan.university.gradproject2.controller.AppConfigController.APP_CONFIGS_URL;

@RestController
@RequestMapping(APP_CONFIGS_URL)
@Slf4j
public class AppConfigController {
    protected final static String APP_CONFIGS_URL = "/api/app-configs";
    private final AppConfigService appConfigService;
    private final AppConfigMapper appConfigMapper;

    public AppConfigController(AppConfigService appConfigService, AppConfigMapper appConfigMapper) {
        this.appConfigService = appConfigService;
        this.appConfigMapper = appConfigMapper;
    }

    @GetMapping
    public List<AppConfigResource> getAllConfigs() {
        return appConfigService.findAll();
    }

    @GetMapping("/{key}")
    public AppConfigResource getConfigByKey(@PathVariable String key) {
        return appConfigService.findByKey(key);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppConfigResource createConfig(@RequestBody AppConfigRequest request) {
        AppConfig appConfig = appConfigMapper.toModel(request);
        return appConfigService.save(appConfig);
    }

    @PutMapping("/{key}")
    public AppConfigResource updateConfig(@PathVariable String key, @RequestBody AppConfigRequest request) {
        // Ensure the key in the path matches the key in the body
        if (!key.equals(request.getKey())) {
            throw new IllegalArgumentException("Key in path must match key in body");
        }
        AppConfig appConfig = appConfigMapper.toModel(request);
        return appConfigService.save(appConfig);
    }

    @DeleteMapping("/{key}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConfig(@PathVariable String key) {
        appConfigService.deleteByKey(key);
    }
}
