package jordan.university.gradproject2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public ActivityFormService activityFormService() {
        return new ActivityFormService();
    }
}