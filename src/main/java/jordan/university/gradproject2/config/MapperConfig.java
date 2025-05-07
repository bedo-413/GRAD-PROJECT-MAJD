package jordan.university.gradproject2.config;

import jordan.university.gradproject2.mapper.ActivityFormMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ActivityFormMapper activityFormMapper() {
        return null;
    }
}