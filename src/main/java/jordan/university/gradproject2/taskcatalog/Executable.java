package jordan.university.gradproject2.taskcatalog;

import jakarta.transaction.Transactional;
import jordan.university.gradproject2.model.ActivityForm;
import jordan.university.gradproject2.repository.activity.ActivityFormRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Executable implements ExecutionTask {

    private final ActivityFormRepository activityFormRepository;

    protected Executable(ActivityFormRepository activityFormRepository) {
        this.activityFormRepository = activityFormRepository;
    }

    @Override
    @Transactional
    public void execute(ActivityForm activityForm) {
        try {
            log.info("Started Running Task {} for Task Catalog {} on form uuid {} with form status {}", activityForm.getActivityType(), activityForm.getTaskCatalog(), activityForm.getUuid(), activityForm.getStatus());
            run(activityForm);
            activityFormRepository.save(activityForm);
        } catch (Exception e) {
            log.info("Failed Running Task {} for Task Catalog {} on form uuid {} with form status {}", activityForm.getActivityType(), activityForm.getTaskCatalog(), activityForm.getUuid(), activityForm.getStatus());
            handleFailedActivityForm();
        }
    }

    protected abstract void run(ActivityForm activityForm);

    private void handleFailedActivityForm() {

    }
}
