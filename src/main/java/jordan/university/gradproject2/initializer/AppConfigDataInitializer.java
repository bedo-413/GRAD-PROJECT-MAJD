package jordan.university.gradproject2.initializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jordan.university.gradproject2.entity.AppConfigEntity;
import jordan.university.gradproject2.repository.appconfig.AppConfigJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Profile("init-configs")
public class AppConfigDataInitializer implements CommandLineRunner {

    private final AppConfigJpaRepository appConfigJpaRepository;

    public AppConfigDataInitializer(AppConfigJpaRepository appConfigJpaRepository) {
        this.appConfigJpaRepository = appConfigJpaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = getClass().getResourceAsStream("/init-data/init-app-configs.json");

        if (inputStream == null) {
            System.out.println("❌ init-app-configs.json not found.");
            return;
        }

        List<AppConfigEntity> inputConfigs = mapper.readValue(inputStream, new TypeReference<>() {});
        Map<String, AppConfigEntity> existingConfigs = appConfigJpaRepository
                .findAll()
                .stream()
                .collect(Collectors.toMap(AppConfigEntity::getKey, c -> c));

        List<AppConfigEntity> toSave = new ArrayList<>();

        for (AppConfigEntity inputConfig : inputConfigs) {
            AppConfigEntity existing = existingConfigs.get(inputConfig.getKey());

            if (existing == null) {
                toSave.add(inputConfig); // New config
            } else if (hasChanged(existing, inputConfig)) {
                inputConfig.setId(existing.getId()); // Ensure update, not insert
                toSave.add(inputConfig); // Update if changed
            }
        }

        if (!toSave.isEmpty()) {
            appConfigJpaRepository.saveAll(toSave);
            System.out.println("✅ Synced " + toSave.size() + " app configs.");
        } else {
            System.out.println("ℹ️ No app config updates needed.");
        }
    }

    private boolean hasChanged(AppConfigEntity existing, AppConfigEntity incoming) {
        return !Objects.equals(existing.getValue(), incoming.getValue());
    }
}