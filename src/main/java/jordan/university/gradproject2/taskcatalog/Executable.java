//package jordan.university.gradproject2.taskcatalog;
//
//import jakarta.transaction.Transactional;
//import jordan.university.gradproject2.enums.Status;
//import jordan.university.gradproject2.enums.WorkflowAction;
//import jordan.university.gradproject2.model.ActivityForm;
//import jordan.university.gradproject2.repository.activity.ActivityFormRepository;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public abstract class Executable implements ExecutionTask {
//
//    private final ActivityFormRepository activityFormRepository;
//
//    protected Executable(ActivityFormRepository activityFormRepository) {
//        this.activityFormRepository = activityFormRepository;
//    }
//
//    @Override
//    @Transactional
//    public void execute(ActivityForm activityForm, WorkflowAction action) {
//        try {
//            log.info("Started Running Task {} for Task Catalog {} on form uuid {} with form status {}", activityForm.getActivityType(), activityForm.getTaskCatalog(), activityForm.getUuid(), activityForm.getStatus());
//            run(activityForm);
//            activityFormRepository.save(activityForm);
//        } catch (Exception e) {
//            log.info("Failed Running Task {} for Task Catalog {} on form uuid {} with form status {}", activityForm.getActivityType(), activityForm.getTaskCatalog(), activityForm.getUuid(), activityForm.getStatus());
//            handleFailedActivityForm(activityForm);
//        }
//    }
//
//    protected void updateStatus(ActivityForm form, Status newStatus) {
//        Status currentStatus = form.getStatus();
//        if (StatusTransitionManager.canTransition(currentStatus, newStatus)) {
//            form.setStatus(newStatus);
//            log.info("Transitioned form uuid {} from {} to {}", form.getUuid(), currentStatus, newStatus);
//        } else {
//            throw new IllegalStateException("Invalid status transition from " + currentStatus + " to " + newStatus);
//        }
//    }
//
//
//    protected abstract void run(ActivityForm activityForm);
//
//    private void handleFailedActivityForm(ActivityForm activityForm) {
//        activityForm.setStatus(Status.REJECTED);
//    }
//}
