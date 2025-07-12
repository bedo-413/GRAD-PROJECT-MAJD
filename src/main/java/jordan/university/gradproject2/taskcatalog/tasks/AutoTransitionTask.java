package jordan.university.gradproject2.taskcatalog.tasks;

import jordan.university.gradproject2.enums.Status;
import jordan.university.gradproject2.mapper.UserMapper;
import jordan.university.gradproject2.model.ActivityForm;
import jordan.university.gradproject2.model.User;
import jordan.university.gradproject2.repository.activity.ActivityFormJpaRepository;
import jordan.university.gradproject2.repository.activity.ActivityFormRepository;
import jordan.university.gradproject2.repository.user.UserJpaRepository;
import jordan.university.gradproject2.service.EmailNotificationService;
import jordan.university.gradproject2.service.logger.ActivityFormLoggerService;
import jordan.university.gradproject2.taskcatalog.ExecutableV2;

public class AutoTransitionTask extends ExecutableV2 {

    private final EmailNotificationService emailNotificationService;

    public AutoTransitionTask(ActivityFormRepository repository,
                              ActivityFormJpaRepository activityFormJpaRepository,
                              UserJpaRepository userJpaRepository,
                              UserMapper userMapper,
                              EmailNotificationService emailNotificationService,
                              ActivityFormLoggerService loggerService) {
        super(repository, activityFormJpaRepository, userJpaRepository, userMapper, loggerService);
        this.emailNotificationService = emailNotificationService;
    }

    @Override
    protected void run(ActivityForm activityForm) {
        User student = activityForm.getStudent();
        User supervisor = activityForm.getSupervisor();
        Status currentStatus = activityForm.getStatus();
        String subject = "Activity Form Status Update - " + currentStatus;

        if (student != null && student.getEmail() != null) {
            emailNotificationService.sendNotification(
                    student.getEmail(),
                    subject,
                    activityForm
            );
        }

        // Send email to supervisor if available
//        if (supervisor != null) {
//            // Prepare data for supervisor email
//
//            // Get supervisor email from the User model
//            String supervisorEmail = supervisor.getEmail();
//
//            // Send notification to supervisor if email is available
//            if (supervisorEmail != null && !supervisorEmail.isEmpty()) {
//                emailNotificationService.sendNotification(
//                        supervisorEmail,
//                        subject,
//                        activityForm
//                );
//            }
//        }

        // Send email to status-based recipient if configured
        String statusRecipient = emailNotificationService.getStatusBasedRecipient(currentStatus);
        if (statusRecipient != null && !statusRecipient.isEmpty()) {

            // Send notification to status-based recipient
            emailNotificationService.sendNotification(
                    statusRecipient,
                    subject,
                    activityForm
            );
        }
    }


    // No logic here, handled in execute() //TODO: FOR NOW I DONT NEED TO PUT ANYTHING IN THE RUN METHOD BUT BELOW IS SOME EXAMPLES FOR FUTURE PURPOSES
    //TODO: SIDE EFFECTS OR NOTIFICATIONS
//        notificationService.sendStatusChangeNotification(activityForm);
//        auditService.recordStatusChange(activityForm);
    //TODO: ENRICHMENTS OR TRANSFORMATIONS
//        activityForm.setLastModifiedBy(currentUserService.getCurrentUser());
//        activityForm.setLastModifiedDate(LocalDateTime.now());
    //TODO: INTEGRATION WITH EXTERNAL SYSTEMS
//        externalSystemService.updateStatus(activityForm.getId(), activityForm.getStatus());

}
