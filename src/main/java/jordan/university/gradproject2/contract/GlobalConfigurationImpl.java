package jordan.university.gradproject2.contract;

import jordan.university.gradproject2.model.AppConfig;
import jordan.university.gradproject2.repository.appconfig.AppConfigRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GlobalConfigurationImpl implements GlobalConfiguration {

    private static final String PERMITTED_USERS_FOR_PASS_THROUGH = "permittedUsersForPassThrough";

    private final AppConfigRepository appConfigRepository;

    public GlobalConfigurationImpl(AppConfigRepository appConfigRepository) {
        this.appConfigRepository = appConfigRepository;
    }

    @Override
    public List<String> getPermittedUsers() {
        Optional<AppConfig> permittedUsers = appConfigRepository.findByKey(PERMITTED_USERS_FOR_PASS_THROUGH);
        return permittedUsers
                .map(config -> Arrays.stream(config.getValue().split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

}
