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
    public ActivityForm findByUuid(String uuid) {
        return mapper.toModel(activityFormJpaRepository.findByUuid(uuid).orElse(null));
    }

    @Override
    public void deleteByUuid(String uuid) {
        activityFormJpaRepository.deleteByUuid(uuid);
    }

    public ActivityForm save(ActivityForm activityForm) {
        return mapper.toModel(activityFormJpaRepository.save(mapper.toEntity(activityForm)));
    }

    @Override
    public List<ActivityForm> findAll() {
        return mapper.toModel(activityFormJpaRepository.findAll());
    }

}