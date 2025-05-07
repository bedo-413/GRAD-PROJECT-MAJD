package jordan.university.gradproject2.config;

import jordan.university.gradproject2.mapper.ActivityFormMapper;
import jordan.university.gradproject2.repository.activity.ActivityFormJpaRepository;
import jordan.university.gradproject2.repository.activity.ActivityFormRepository;
import jordan.university.gradproject2.service.ActivityFormService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public ActivityFormService activityFormService(ActivityFormRepository activityFormRepository,
                                                   ActivityFormMapper activityFormMapper,
                                                   ActivityFormJpaRepository activityFormJpaRepository) {
        return new ActivityFormService(activityFormRepository, activityFormMapper, activityFormJpaRepository);
    }
}