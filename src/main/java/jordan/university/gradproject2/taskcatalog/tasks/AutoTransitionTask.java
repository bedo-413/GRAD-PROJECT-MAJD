package jordan.university.gradproject2.taskcatalog.tasks;

import jordan.university.gradproject2.mapper.UserMapper;
import jordan.university.gradproject2.model.ActivityForm;
import jordan.university.gradproject2.repository.activity.ActivityFormRepository;
import jordan.university.gradproject2.repository.user.UserJpaRepository;
import jordan.university.gradproject2.service.EmailNotificationService;
import jordan.university.gradproject2.taskcatalog.ExecutableV2;

public class AutoTransitionTask extends ExecutableV2 {

    private final EmailNotificationService emailNotificationService;

    public AutoTransitionTask(ActivityFormRepository repository,
                              UserJpaRepository userJpaRepository,
                              UserMapper userMapper, EmailNotificationService emailNotificationService) {
        super(repository, userJpaRepository, userMapper);
        this.emailNotificationService = emailNotificationService;
    }

    @Override
    protected void run(ActivityForm activityForm) {
        // No logic here, handled in execute() //TODO: FOR NOW I DONT NEED TO PUT ANYTHING IN THE RUN METHOD BUT BELOW IS SOME EXAMPLES FOR FUTURE PURPOSES
        //TODO: SIDE EFFECTS OR NOTIFICATIONS
//        notificationService.sendStatusChangeNotification(activityForm);
//        auditService.recordStatusChange(activityForm);
        //TODO: ENRICHMENTS OR TRANSFORMATIONS
//        activityForm.setLastModifiedBy(currentUserService.getCurrentUser());
//        activityForm.setLastModifiedDate(LocalDateTime.now());
        //TODO: INTEGRATION WITH EXTERNAL SYSTEMS
//        externalSystemService.updateStatus(activityForm.getId(), activityForm.getStatus());

        // Check if a reminder notification should be sent
        //if (shouldSendReminder(activityForm))
        //emailNotificationService.sendSimpleNotification(activityForm);

    }

    // Helper method to determine if a reminder should be sent
//    private boolean shouldSendReminder(ActivityForm activityForm) {
//        // Implement your logic here to determine if a reminder should be sent
//        // For example, send a reminder if the form has been in the current status for more than 3 days
//        return activityForm.getDaysInCurrentStatus() > 3;
//    }
}