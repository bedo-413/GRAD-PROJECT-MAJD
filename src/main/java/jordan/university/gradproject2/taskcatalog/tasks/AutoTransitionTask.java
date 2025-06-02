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
        // Send email notifications to both student and supervisor
        User student = activityForm.getStudent();
        User supervisor = activityForm.getSupervisor();
        Status currentStatus = activityForm.getStatus();
        String subject = "Activity Form Status Update - " + currentStatus;

        // Send email to student if available
        if (student != null && student.getEmail() != null) {
            // Prepare data for student email
            String studentContent = emailNotificationService.createEmailContent(activityForm, student, true);

            // Send notification to student
            emailNotificationService.sendSimpleNotification(
                    student.getEmail(),
                    subject,
                    studentContent,
                    activityForm
            );
        }

        // Send email to supervisor if available
        if (supervisor != null) {
            // Prepare data for supervisor email
            String supervisorContent = emailNotificationService.createEmailContent(activityForm, supervisor, false);

            // Get supervisor email from the User model
            String supervisorEmail = supervisor.getEmail();

            // Send notification to supervisor if email is available
            if (supervisorEmail != null && !supervisorEmail.isEmpty()) {
                emailNotificationService.sendSimpleNotification(
                        supervisorEmail,
                        subject,
                        supervisorContent,
                        activityForm
                );
            }
        }

        // Send email to status-based recipient if configured
        String statusRecipient = emailNotificationService.getStatusBasedRecipient(currentStatus);
        if (statusRecipient != null && !statusRecipient.isEmpty()) {
            // Create generic content for status-based recipient
            String genericContent = "An activity form has been updated to status: " + currentStatus + ".\n\n" +
                    "Activity Details:\n" +
                    "- Activity Type: " + activityForm.getActivityType() + "\n" +
                    "- Activity Date: " + activityForm.getActivityDate() + "\n" +
                    "- Organizing Entity: " + activityForm.getOrganizingEntity() + "\n" +
                    "- Location: " + activityForm.getLocation() + "\n" +
                    "- Start Time: " + activityForm.getStartTime() + "\n" +
                    "- End Time: " + activityForm.getEndTime() + "\n" +
                    "- Description: " + activityForm.getDescription() + "\n\n" +
                    "Student: " + (student != null ? student.getFirstName() + " " + student.getLastName() : "N/A") + "\n" +
                    "Supervisor: " + (supervisor != null ? supervisor.getFirstName() + " " + supervisor.getLastName() : "N/A") + "\n\n" +
                    "Please log in to the system for more details.\n\n" +
                    "Best regards,\nGraduation Project System";

            // Send notification to status-based recipient
            emailNotificationService.sendSimpleNotification(
                    statusRecipient,
                    subject,
                    genericContent,
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
