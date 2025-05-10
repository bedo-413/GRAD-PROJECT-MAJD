package jordan.university.gradproject2.taskcatalog.tasks;

import jordan.university.gradproject2.model.ActivityForm;
import jordan.university.gradproject2.repository.activity.ActivityFormRepository;
import jordan.university.gradproject2.taskcatalog.ExecutableV2;

public class AutoTransitionTask extends ExecutableV2 {

    public AutoTransitionTask(ActivityFormRepository repository) {
        super(repository);
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
    }
}