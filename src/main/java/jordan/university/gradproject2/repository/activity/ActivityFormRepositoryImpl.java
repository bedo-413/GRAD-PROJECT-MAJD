package jordan.university.gradproject2.repository.activity;

import jordan.university.gradproject2.mapper.ActivityFormMapper;
import jordan.university.gradproject2.model.ActivityForm;

import java.util.List;

public class ActivityFormRepositoryImpl implements ActivityFormRepository {

    private final ActivityFormJpaRepository activityFormJpaRepository;
    private final ActivityFormMapper mapper;

    public ActivityFormRepositoryImpl(ActivityFormJpaRepository activityFormJpaRepository,
                                      ActivityFormMapper mapper) {
        this.activityFormJpaRepository = activityFormJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ActivityForm findById(String id) {
        return mapper.toModel(activityFormJpaRepository.findById(id).orElse(null));
    }

    @Override
    public void deleteById(String id) {
        activityFormJpaRepository.deleteById(id);
    }

    @Override
    public List<ActivityForm> findAll() {
        return mapper.toModel(activityFormJpaRepository.findAll());
    }

}