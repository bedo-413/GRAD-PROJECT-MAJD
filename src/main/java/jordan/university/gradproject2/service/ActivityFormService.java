package jordan.university.gradproject2.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jordan.university.gradproject2.entity.ActivityFormEntity;
import jordan.university.gradproject2.entity.UserEntity;
import jordan.university.gradproject2.enums.WorkflowAction;
import jordan.university.gradproject2.mapper.ActivityFormMapper;
import jordan.university.gradproject2.mapper.UserMapper;
import jordan.university.gradproject2.model.ActivityForm;
import jordan.university.gradproject2.model.User;
import jordan.university.gradproject2.repository.activity.ActivityFormJpaRepository;
import jordan.university.gradproject2.repository.activity.ActivityFormRepository;
import jordan.university.gradproject2.repository.activitylog.ActivityFormLogRepository;
import jordan.university.gradproject2.repository.user.UserJpaRepository;
import jordan.university.gradproject2.resource.ActivityFormLogResource;
import jordan.university.gradproject2.resource.ActivityFormResource;
import jordan.university.gradproject2.service.logger.ActivityFormLoggerService;
import jordan.university.gradproject2.taskcatalog.TaskCatalog;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ActivityFormService {
    private final ActivityFormRepository activityFormRepository;
    private final UserJpaRepository userRepository;
    private final ActivityFormMapper activityFormMapper;
    private final ActivityFormJpaRepository activityFormJpaRepository;
    private final TaskCatalog taskCatalog;
    private final ActivityFormLogRepository formLogRepository;
    private final ActivityFormLoggerService loggerService;
    private final EmailNotificationService emailNotificationService;
    private final UserMapper userMapper;

    public ActivityFormService(ActivityFormRepository activityFormRepository,
                               UserJpaRepository userRepository,
                               ActivityFormMapper activityFormMapper,
                               ActivityFormJpaRepository activityFormJpaRepository,
                               TaskCatalog taskCatalog,
                               ActivityFormLogRepository formLogRepository,
                               ActivityFormLoggerService loggerService, EmailNotificationService emailNotificationService, UserMapper userMapper) {
        this.activityFormRepository = activityFormRepository;
        this.userRepository = userRepository;
        this.activityFormMapper = activityFormMapper;
        this.activityFormJpaRepository = activityFormJpaRepository;
        this.taskCatalog = taskCatalog;
        this.formLogRepository = formLogRepository;
        this.loggerService = loggerService;
        this.emailNotificationService = emailNotificationService;
        this.userMapper = userMapper;
    }


    @Transactional
    public ActivityForm transitionFormAndUpdateStatus(String uuid, WorkflowAction action) {
        ActivityForm form = activityFormRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("ActivityForm with uuid " + uuid + " not found"));

        form.setTaskCatalog(taskCatalog);
        form.setWorkflowAction(action);
        form.run();

        return activityFormRepository.save(form);
    }

    @Transactional
    public ActivityForm findByUuid(String uuid) {
        return activityFormRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ActivityForm not found"));
    }

    @Transactional
    public List<ActivityFormLogResource> getLogsByFormUuid(String uuid) {
        return formLogRepository.findByFormUuidOrderByTimestampAsc(uuid);
    }

    @Transactional
    public List<ActivityFormResource> findAll() {
        return activityFormRepository.findAll()
                .stream()
                .map(activityFormMapper::toResource)
                .collect(Collectors.toList());
    }

    @Transactional
    public ActivityFormResource create(ActivityForm activityForm) {
        activityForm.setUuid(UUID.randomUUID().toString());
        Long studentId = activityForm.getStudent().getId();
        if (studentId == null) {
            throw new IllegalArgumentException("Student ID cannot be null");
        }
        Long supervisorId = activityForm.getSupervisor().getId();
        if (supervisorId == null) {
            throw new IllegalArgumentException("Supervisor ID cannot be null");
        }
        UserEntity studentEntity = userRepository.getReferenceById(studentId);
        UserEntity supervisorEntity = userRepository.getReferenceById(supervisorId);
        ActivityFormEntity entity = activityFormMapper.toEntity(activityForm);
        entity.setStudent(studentEntity);
        entity.setSupervisor(supervisorEntity);
        activityFormJpaRepository.save(entity);
        activityForm.setId(entity.getId());
        sendStudentEmail(userMapper.toModel(studentEntity), activityForm);
        //sendSupervisorEmail(userMapper.toModel(supervisorEntity), activityForm);
        loggerService.log(entity, null, activityForm.getStatus(), WorkflowAction.CREATE);
        return activityFormMapper.toResource(entity);
    }

    private void sendStudentEmail(User student, ActivityForm activityForm) {
        if (student != null && student.getEmail() != null) {
            String studentContent = emailNotificationService.createEmailContent(activityForm, student, true);
            String subject = "Activity Form Status Update - " + activityForm.getStatus();
            emailNotificationService.sendSimpleNotification(
                    student.getEmail(),
                    subject,
                    studentContent,
                    activityForm
            );
        }
    }

    private void sendSupervisorEmail(User supervisor, ActivityForm activityForm) {
        if (supervisor != null) {
            String supervisorContent = emailNotificationService.createEmailContent(activityForm, supervisor, false);
            String subject = "Activity Form Status Update - " + activityForm.getStatus();
            String supervisorEmail = supervisor.getEmail();
            if (supervisorEmail != null && !supervisorEmail.isEmpty()) {
                emailNotificationService.sendSimpleNotification(
                        supervisorEmail,
                        subject,
                        supervisorContent,
                        activityForm
                );
            }
        }
    }

    @Transactional
    public void delete(ActivityForm activityForm) {
        ActivityFormEntity entity = activityFormMapper.toEntity(activityForm);
        activityFormRepository.deleteByUuid(entity.getUuid());
    }

}
