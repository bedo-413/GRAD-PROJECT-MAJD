package jordan.university.gradproject2.service;

import jakarta.transaction.Transactional;
import jordan.university.gradproject2.entity.ActivityFormEntity;
import jordan.university.gradproject2.mapper.ActivityFormMapper;
import jordan.university.gradproject2.model.ActivityForm;
import jordan.university.gradproject2.repository.ActivityFormRepository;
import jordan.university.gradproject2.resource.ActivityFormResource;
import org.springframework.stereotype.Service;

@Service
public class ActivityFormService {
    private final ActivityFormRepository activityFormRepository;
    private final ActivityFormMapper activityFormMapper;

    public ActivityFormService(ActivityFormRepository activityFormRepository, ActivityFormMapper activityFormMapper) {
        this.activityFormRepository = activityFormRepository;
        this.activityFormMapper = activityFormMapper;
    }


    public List<ActivityResponse> findAll() {
        return activityRepository.findAll()
                .stream()
                .map(activityMapper::toActivityResponse)
                .collect(Collectors.toList());
    }

    public ActivityResponse findById(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found with id: " + id));
        return activityMapper.toActivityResponse(activity);
    }

    @Transactional
    public ActivityResponse create(ActivityRequest request) {
        Activity activity = activityMapper.toActivity(request);
        Activity savedActivity = activityRepository.save(activity);
        return activityMapper.toActivityResponse(savedActivity);
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
        activityFormRepository.deleteById(entity.getId());
    }
}