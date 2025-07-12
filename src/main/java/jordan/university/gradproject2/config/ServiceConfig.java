package jordan.university.gradproject2.config;

import jordan.university.gradproject2.contract.GlobalConfiguration;
import jordan.university.gradproject2.mapper.ActivityFormMapper;
import jordan.university.gradproject2.mapper.AppConfigMapper;
import jordan.university.gradproject2.mapper.UserMapper;
import jordan.university.gradproject2.repository.activity.ActivityFormJpaRepository;
import jordan.university.gradproject2.repository.activity.ActivityFormRepository;
import jordan.university.gradproject2.repository.activitylog.ActivityFormLogRepository;
import jordan.university.gradproject2.repository.appconfig.AppConfigRepository;
import jordan.university.gradproject2.repository.user.UserJpaRepository;
import jordan.university.gradproject2.repository.user.UserRepository;
import jordan.university.gradproject2.security.SecurityUtilService;
import jordan.university.gradproject2.service.*;
import jordan.university.gradproject2.service.logger.ActivityFormLoggerService;
import jordan.university.gradproject2.taskcatalog.TaskCatalog;
import jordan.university.gradproject2.transformer.ActivityFormTransformer;
import jordan.university.gradproject2.validation.ValidationModel;
import jordan.university.gradproject2.validation.service.ValidationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public ActivityFormService activityFormService(ActivityFormRepository activityFormRepository,
                                                   UserJpaRepository userJpaRepository,
                                                   ActivityFormMapper activityFormMapper,
                                                   ActivityFormJpaRepository activityFormJpaRepository,
                                                   TaskCatalog taskCatalog,
                                                   ActivityFormLogRepository formLogRepository,
                                                   ActivityFormLoggerService activityFormLoggerService,
                                                   EmailNotificationService emailNotificationService,
                                                   UserMapper userMapper,
                                                   ValidationService validationService,
                                                   ValidationModel activityFormCreationValidation,
                                                   UserRepository userRepository,
                                                   GlobalConfiguration globalConfiguration,
                                                   ActivityFormTransformer activityFormTransformer) {
        return new ActivityFormService(activityFormRepository, userJpaRepository, activityFormMapper, activityFormJpaRepository,
                taskCatalog, formLogRepository, activityFormLoggerService, emailNotificationService, userMapper,
                validationService, activityFormCreationValidation, userRepository, globalConfiguration, activityFormTransformer);
    }

    @Bean
    public LinksService linksService() {
        return new LinksService();
    }

    @Bean
    public UserService userService(UserRepository userRepository, UserMapper userMapper) {
        return new UserService(userRepository, userMapper);
    }

    @Bean
    public ActivityFormLoggerService activityFormLoggerService(SecurityUtilService securityUtilService, ActivityFormLogRepository activityFormLogRepository) {
        return new ActivityFormLoggerService(securityUtilService, activityFormLogRepository);
    }

    @Bean
    public AppConfigService appConfigService(AppConfigRepository appConfigRepository, AppConfigMapper appConfigMapper) {
        return new AppConfigService(appConfigRepository, appConfigMapper);
    }
}
