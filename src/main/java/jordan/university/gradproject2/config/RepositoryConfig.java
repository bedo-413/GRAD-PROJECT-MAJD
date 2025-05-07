package jordan.university.gradproject2.config;

import jordan.university.gradproject2.repository.ActivityFormRepository;
import jordan.university.gradproject2.repository.ActivityFormRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public ActivityFormRepository activityFormRepository() {
        return new ActivityFormRepositoryImpl();
    }
}
