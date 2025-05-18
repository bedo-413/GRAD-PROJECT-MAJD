package jordan.university.gradproject2.config;

import jordan.university.gradproject2.mapper.UserMapper;
import jordan.university.gradproject2.repository.activity.ActivityFormRepository;
import jordan.university.gradproject2.repository.user.UserJpaRepository;
import jordan.university.gradproject2.service.EmailNotificationService;
import jordan.university.gradproject2.taskcatalog.TaskCatalog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StateConfig {

    @Bean
    public TaskCatalog taskCatalog(ActivityFormRepository repository,
                                   UserJpaRepository userJpaRepository,
                                   UserMapper userMapper,
                                   EmailNotificationService emailNotificationService) {
        return new TaskCatalog(repository, userJpaRepository, userMapper, emailNotificationService);
    }
}
