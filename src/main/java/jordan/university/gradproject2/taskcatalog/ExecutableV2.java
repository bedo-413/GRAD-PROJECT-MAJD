package jordan.university.gradproject2.taskcatalog;

import jakarta.transaction.Transactional;
import jordan.university.gradproject2.entity.UserEntity;
import jordan.university.gradproject2.enums.Status;
import jordan.university.gradproject2.enums.WorkflowAction;
import jordan.university.gradproject2.mapper.UserMapper;
import jordan.university.gradproject2.model.ActivityForm;
import jordan.university.gradproject2.repository.activity.ActivityFormRepository;
import jordan.university.gradproject2.repository.user.UserJpaRepository;
import jordan.university.gradproject2.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public abstract class ExecutableV2 implements ExecutionTask {

    private final ActivityFormRepository activityFormRepository;
    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    protected ExecutableV2(ActivityFormRepository activityFormRepository, UserJpaRepository userJpaRepository, UserMapper userMapper) {
        this.activityFormRepository = activityFormRepository;
        this.userJpaRepository = userJpaRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public void execute(ActivityForm activityForm, WorkflowAction action) {
        try {
            log.info("Started Task for form uuid {} with status {}", activityForm.getUuid(), activityForm.getStatus());

            Status current = activityForm.getStatus();
            Optional<Status> next = StatusTransitionManagerV2.getNextStatus(current, action);

            Long studentId = activityForm.getStudent().getId();
            UserEntity managedStudent = userJpaRepository.getReferenceById(studentId);
            activityForm.setStudent(userMapper.toModel(managedStudent));

            run(activityForm);

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
