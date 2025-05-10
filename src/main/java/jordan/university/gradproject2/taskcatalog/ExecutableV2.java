package jordan.university.gradproject2.taskcatalog;

import jakarta.transaction.Transactional;
import jordan.university.gradproject2.enums.Status;
import jordan.university.gradproject2.enums.WorkflowAction;
import jordan.university.gradproject2.model.ActivityForm;
import jordan.university.gradproject2.repository.activity.ActivityFormRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public abstract class ExecutableV2 implements ExecutionTask {

    private final ActivityFormRepository activityFormRepository;

    protected ExecutableV2(ActivityFormRepository activityFormRepository) {
        this.activityFormRepository = activityFormRepository;
    }

    @Override
    @Transactional
    public void execute(ActivityForm activityForm, WorkflowAction action) {
        try {
            log.info("Started Task for form uuid {} with status {}", activityForm.getUuid(), activityForm.getStatus());

            Status current = activityForm.getStatus();
            Optional<Status> next = StatusTransitionManagerV2.getNextStatus(current, action);

            if (next.isPresent()) {
                activityForm.setStatus(next.get());
                log.info("Form {} moved from {} → {}", activityForm.getUuid(), current, next.get());
                activityFormRepository.save(activityForm);
            } else {
                throw new IllegalStateException("No valid transition from " + current + " using action " + action);
            }

        } catch (Exception e) {
            log.error("Failed task for form {} at status {}", activityForm.getUuid(), activityForm.getStatus(), e);
            handleFailedActivityForm(activityForm);
        }
    }

    protected abstract void run(ActivityForm activityForm);

    private void handleFailedActivityForm(ActivityForm activityForm) {
        activityForm.setStatus(Status.REJECTED);
    }
}
