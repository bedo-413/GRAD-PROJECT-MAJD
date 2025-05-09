package jordan.university.gradproject2.config;

import jordan.university.gradproject2.mapper.ActivityFormMapper;
import jordan.university.gradproject2.repository.activity.ActivityFormJpaRepository;
import jordan.university.gradproject2.repository.activity.ActivityFormRepository;
import jordan.university.gradproject2.service.ActivityFormService;
import jordan.university.gradproject2.service.LinksService;
import jordan.university.gradproject2.taskcatalog.TaskCatalog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public ActivityFormService activityFormService(ActivityFormRepository activityFormRepository,
                                                   ActivityFormMapper activityFormMapper,
                                                   ActivityFormJpaRepository activityFormJpaRepository,
                                                   TaskCatalog taskCatalog) {
        return new ActivityFormService(activityFormRepository, activityFormMapper, activityFormJpaRepository, taskCatalog);
    }

    @Bean
    public LinksService linksService() {
        return new LinksService();
    }
}