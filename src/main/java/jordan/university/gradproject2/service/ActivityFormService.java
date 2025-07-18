package jordan.university.gradproject2.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jordan.university.gradproject2.entity.ActivityFormEntity;
import jordan.university.gradproject2.enums.WorkflowAction;
import jordan.university.gradproject2.mapper.ActivityFormMapper;
import jordan.university.gradproject2.model.ActivityForm;
import jordan.university.gradproject2.model.User;
import jordan.university.gradproject2.repository.activity.ActivityFormJpaRepository;
import jordan.university.gradproject2.repository.activity.ActivityFormRepository;
import jordan.university.gradproject2.repository.activitylog.ActivityFormLogRepository;
import jordan.university.gradproject2.resource.ActivityFormLogResource;
import jordan.university.gradproject2.resource.ActivityFormResource;
import jordan.university.gradproject2.service.logger.ActivityFormLoggerService;
import jordan.university.gradproject2.taskcatalog.TaskCatalog;
import jordan.university.gradproject2.transformer.ActivityFormTransformer;
import jordan.university.gradproject2.validation.ValidationError;
import jordan.university.gradproject2.validation.ValidationModel;
import jordan.university.gradproject2.validation.service.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class ActivityFormService {
    private final ActivityFormRepository activityFormRepository;
    private final ActivityFormMapper activityFormMapper;
    private final ActivityFormJpaRepository activityFormJpaRepository;
    private final TaskCatalog taskCatalog;
    private final ActivityFormLogRepository formLogRepository;
    private final ActivityFormLoggerService loggerService;
    private final EmailNotificationService emailNotificationService;
    private final ValidationService validationService;
    private final ValidationModel activityFormCreationValidation;
    private final ActivityFormTransformer formToModelTransformer;

    public ActivityFormService(ActivityFormRepository activityFormRepository,
                               ActivityFormMapper activityFormMapper,
                               ActivityFormJpaRepository activityFormJpaRepository,
                               TaskCatalog taskCatalog,
                               ActivityFormLogRepository formLogRepository,
                               ActivityFormLoggerService loggerService,
                               EmailNotificationService emailNotificationService,
                               ValidationService validationService,
                               ValidationModel activityFormCreationValidation,
                               ActivityFormTransformer formToModelTransformer) {
        this.activityFormRepository = activityFormRepository;
        this.activityFormMapper = activityFormMapper;
        this.activityFormJpaRepository = activityFormJpaRepository;
        this.taskCatalog = taskCatalog;
        this.formLogRepository = formLogRepository;
        this.loggerService = loggerService;
        this.emailNotificationService = emailNotificationService;
        this.validationService = validationService;
        this.activityFormCreationValidation = activityFormCreationValidation;
        this.formToModelTransformer = formToModelTransformer;
    }


    @Transactional
    public ActivityForm transitionFormAndUpdateStatus(String uuid, WorkflowAction action) {
        ActivityForm form = activityFormRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("ActivityForm with uuid " + uuid + " not found"));

        validateAndPreventTransition(form, activityFormCreationValidation);

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
        formToModelTransformer.transform(activityForm);
        validate(activityForm, activityFormCreationValidation);
        ActivityFormEntity entity = activityFormMapper.toEntity(activityForm);
        activityFormJpaRepository.save(entity);
        activityForm.setId(entity.getId());
        sendStudentEmail(activityForm.getStudent(), activityForm);
        //sendSupervisorEmail(activityForm.getSupervisor(), activityForm);
        loggerService.log(entity, null, activityForm.getStatus(), WorkflowAction.CREATE);
        return activityFormMapper.toResource(entity);
    }

    private void sendStudentEmail(User student, ActivityForm activityForm) {
        if (student != null && student.getEmail() != null) {
            String subject = "Activity Form Status Update - " + activityForm.getStatus();
            emailNotificationService.sendNotification(
                    student.getEmail(),
                    subject,
                    activityForm
            );
        }
    }

    private void sendSupervisorEmail(User supervisor, ActivityForm activityForm) {
        if (supervisor != null) {
            String subject = "Activity Form Status Update - " + activityForm.getStatus();
            String supervisorEmail = supervisor.getEmail();
            if (supervisorEmail != null && !supervisorEmail.isEmpty()) {
                emailNotificationService.sendNotification(
                        supervisorEmail,
                        subject,
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

    private void validate(ActivityForm activityForm, ValidationModel validationModel) {
        List<ValidationError> errors = validationService.validate(activityForm, validationModel);
        activityForm.setErrors(errors);
    }

    private void validateAndPreventTransition(ActivityForm activityForm, ValidationModel validationModel) {
        validate(activityForm, validationModel);
        if (nonNull(activityForm.getErrors()) && !activityForm.getErrors().isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Cannot transition form status. Please fix the following errors: ");
            for (ValidationError error : activityForm.getErrors()) {
                if (error.getField() != null) {
                    errorMessage.append(error.getField()).append(": ");
                }
                errorMessage.append(error.getMessage()).append("; ");
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage.toString());
        }
    }

    @Transactional
    public ActivityFormResource addRemarks(String uuid, List<String> remarks) {
        ActivityForm form = activityFormRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("ActivityForm with uuid " + uuid + " not found"));

        if (form.getRemarks() == null) {
            form.setRemarks(remarks);
        } else {
            form.getRemarks().addAll(remarks);
        }

        ActivityForm savedForm = activityFormRepository.save(form);
        return activityFormMapper.toResource(savedForm);
    }
}
