package jordan.university.gradproject2.config;

import jordan.university.gradproject2.mapper.ActivityFormLogMapper;
import jordan.university.gradproject2.mapper.ActivityFormMapper;
import jordan.university.gradproject2.mapper.EmailNotificationMapper;
import jordan.university.gradproject2.mapper.UserMapper;
import jordan.university.gradproject2.repository.activity.ActivityFormJpaRepository;
import jordan.university.gradproject2.repository.activity.ActivityFormRepository;
import jordan.university.gradproject2.repository.activity.ActivityFormRepositoryImpl;
import jordan.university.gradproject2.repository.activitylog.ActivityFormLogJpaRepository;
import jordan.university.gradproject2.repository.activitylog.ActivityFormLogRepository;
import jordan.university.gradproject2.repository.activitylog.ActivityFormLogRepositoryImpl;
import jordan.university.gradproject2.repository.email.EmailNotificationJpaRepository;
import jordan.university.gradproject2.repository.email.EmailNotificationRepository;
import jordan.university.gradproject2.repository.email.EmailNotificationRepositoryImpl;
import jordan.university.gradproject2.repository.user.UserJpaRepository;
import jordan.university.gradproject2.repository.user.UserRepository;
import jordan.university.gradproject2.repository.user.UserRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public ActivityFormRepository activityFormRepository(ActivityFormJpaRepository activityFormJpaRepository,
                                                         ActivityFormMapper mapper) {
        return new ActivityFormRepositoryImpl(activityFormJpaRepository, mapper);
    }

    @Bean
    public UserRepository userRepository(UserJpaRepository userJpaRepository,
                                         UserMapper mapper) {
        return new UserRepositoryImpl(userJpaRepository, mapper);
    }

    @Bean
    public ActivityFormLogRepository activityFormLogRepository(ActivityFormLogJpaRepository activityFormLogJpaRepository,
                                                               ActivityFormLogMapper activityFormLogMapper) {
        return new ActivityFormLogRepositoryImpl(activityFormLogJpaRepository, activityFormLogMapper);
    }

    @Bean
    public EmailNotificationRepository emailNotificationRepository(EmailNotificationJpaRepository emailNotificationJpaRepository,
                                                                  EmailNotificationMapper emailNotificationMapper) {
        return new EmailNotificationRepositoryImpl(emailNotificationJpaRepository, emailNotificationMapper);
    }
}
