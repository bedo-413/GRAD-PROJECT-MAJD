package jordan.university.gradproject2.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jordan.university.gradproject2.entity.ActivityFormEntity;
import jordan.university.gradproject2.entity.UserEntity;
import jordan.university.gradproject2.enums.WorkflowAction;
import jordan.university.gradproject2.mapper.ActivityFormMapper;
import jordan.university.gradproject2.mapper.UserMapper;
import jordan.university.gradproject2.model.ActivityForm;
import jordan.university.gradproject2.repository.activity.ActivityFormJpaRepository;
import jordan.university.gradproject2.repository.activity.ActivityFormRepository;
import jordan.university.gradproject2.repository.user.UserJpaRepository;
import jordan.university.gradproject2.resource.ActivityFormResource;
import jordan.university.gradproject2.taskcatalog.TaskCatalog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final UserMapper userMapper;
    private final TaskCatalog taskCatalog;

    public ActivityFormService(ActivityFormRepository activityFormRepository, UserJpaRepository userRepository,
                               ActivityFormMapper activityFormMapper,
                               ActivityFormJpaRepository activityFormJpaRepository, UserMapper userMapper,
                               TaskCatalog taskCatalog) {
        this.activityFormRepository = activityFormRepository;
        this.userRepository = userRepository;
        this.activityFormMapper = activityFormMapper;
        this.activityFormJpaRepository = activityFormJpaRepository;
        this.userMapper = userMapper;
        this.taskCatalog = taskCatalog;
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


//    public ActivityFormResource transitionFormAndUpdateStatus(ActivityForm activityForm) {
//        if (activityForm.getStudent() != null && activityForm.getStudent().getUniversityId() != null) {
//            UserEntity student = userRepository.findByUniversityId(activityForm.getStudent().getUniversityId());
//            activityForm.setStudent(userMapper.toModel(student));
//        }
//        activityForm.setTaskCatalog(taskCatalog);
//        activityForm.setWorkflowAction(activityForm.getWorkflowAction()); //IDK WHY WE NEED THIS FOR NOW
//        activityForm.run();
//        return activityFormMapper.toResource(activityForm);
//    }

    @Transactional
    public List<ActivityFormResource> findAll() {
        return activityFormRepository.findAll()
                .stream()
                .map(activityFormMapper::toResource)
                .collect(Collectors.toList());
    }

//    public ActivityFormResource findById(Long id) {
//        Activity activity = activityRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Activity not found with id: " + id));
//        return activityMapper.toActivityResponse(activity);
//    }

    @Transactional
    public ActivityFormResource create(ActivityForm activityForm) {
        activityForm.setUuid(UUID.randomUUID().toString());
        Long studentId = activityForm.getStudent().getId();
        if (studentId == null)
            throw new IllegalArgumentException("Student ID cannot be null");
        UserEntity studentEntity = userRepository.getReferenceById(studentId);
        ActivityFormEntity entity = activityFormMapper.toEntity(activityForm);
        entity.setStudent(studentEntity);
        activityFormJpaRepository.save(entity);
        return activityFormMapper.toResource(entity);
    }


//    @Transactional
//    public ActivityFormResource update(Long id, ActivityForm activityForm) {
//        Activity existingActivity = activityRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Activity not found with id: " + id));
//
//        // Update all fields except createdAt
//        Activity updatedActivity = activityMapper.toActivity(request);
//        updatedActivity.setRequestId(id);
//        updatedActivity.setCreatedAt(existingActivity.getCreatedAt());
//
//        Activity savedActivity = activityRepository.save(updatedActivity);
//        return activityMapper.toActivityResponse(savedActivity);
//    }

    @Transactional
    public void delete(ActivityForm activityForm) {
        ActivityFormEntity entity = activityFormMapper.toEntity(activityForm);
        activityFormRepository.deleteByUuid(entity.getUuid());
    }
}