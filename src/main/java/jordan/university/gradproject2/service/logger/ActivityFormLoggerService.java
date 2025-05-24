package jordan.university.gradproject2.service.logger;

import jordan.university.gradproject2.entity.ActivityFormEntity;
import jordan.university.gradproject2.enums.Status;
import jordan.university.gradproject2.enums.WorkflowAction;
import jordan.university.gradproject2.model.ActivityFormLog;
import jordan.university.gradproject2.model.User;
import jordan.university.gradproject2.repository.activitylog.ActivityFormLogRepository;
import jordan.university.gradproject2.security.SecurityUtilService;

import java.time.LocalDateTime;

public class ActivityFormLoggerService extends GenericEntityLogger<ActivityFormEntity, Status> {

    private final ActivityFormLogRepository formLogRepository;

    public ActivityFormLoggerService(SecurityUtilService securityService, ActivityFormLogRepository formLogRepository) {
        super(securityService);
        this.formLogRepository = formLogRepository;
    }

    @Override
    public void log(ActivityFormEntity form, Status from, Status to, WorkflowAction action) {
        User user = getCurrentUser();

        ActivityFormLog log = new ActivityFormLog();
        log.setForm(form);
        log.setFromStatus(from);
        log.setToStatus(to);
        log.setAction(action);
        log.setPerformedBy(user.getEmail());
        log.setPerformedByRole(user.getOccupation());
        log.setTimestamp(LocalDateTime.now());

        formLogRepository.save(log);
    }
}
