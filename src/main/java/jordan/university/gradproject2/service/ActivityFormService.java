package jordan.university.gradproject2.service;

import jakarta.transaction.Transactional;
import jordan.university.gradproject2.entity.ActivityFormEntity;
import jordan.university.gradproject2.entity.UserEntity;
import jordan.university.gradproject2.mapper.ActivityFormMapper;
import jordan.university.gradproject2.mapper.UserMapper;
import jordan.university.gradproject2.model.ActivityForm;
import jordan.university.gradproject2.repository.activity.ActivityFormJpaRepository;
import jordan.university.gradproject2.repository.activity.ActivityFormRepository;
import jordan.university.gradproject2.repository.user.UserJpaRepository;
import jordan.university.gradproject2.resource.ActivityFormResource;
import jordan.university.gradproject2.taskcatalog.TaskCatalog;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
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


    public ActivityFormResource transitionFormAndUpdateStatus(ActivityForm activityForm) {
        if (activityForm.getStudent() != null && activityForm.getStudent().getUniversityId() != null) {
            UserEntity student = userRepository.findByUniversityId(activityForm.getStudent().getUniversityId());
            activityForm.setStudent(userMapper.toModel(student));
        }
        activityForm.setTaskCatalog(taskCatalog);
        activityForm.setWorkflowAction(activityForm.getWorkflowAction()); //IDK WHY WE NEED THIS FOR NOW
        activityForm.run();
        return activityFormMapper.toResource(activityForm);
    }

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

        // Fetch the existing UserEntity for student/requester
        String studentId = activityForm.getStudent().getUniversityId();
        UserEntity studentEntity = userRepository.findByUniversityId(studentId);

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