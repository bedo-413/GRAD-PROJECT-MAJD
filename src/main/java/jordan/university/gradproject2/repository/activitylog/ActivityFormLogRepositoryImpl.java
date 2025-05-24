package jordan.university.gradproject2.repository.activitylog;

import jordan.university.gradproject2.mapper.ActivityFormLogMapper;
import jordan.university.gradproject2.model.ActivityFormLog;
import jordan.university.gradproject2.resource.ActivityFormLogResource;

import java.util.List;

public class ActivityFormLogRepositoryImpl implements ActivityFormLogRepository {

    private final ActivityFormLogJpaRepository jpaRepository;
    private final ActivityFormLogMapper mapper;

    public ActivityFormLogRepositoryImpl(ActivityFormLogJpaRepository jpaRepository, ActivityFormLogMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ActivityFormLogResource> findByFormUuidOrderByTimestampAsc(String uuid) {
        return jpaRepository.findByFormUuidOrderByTimestampAsc(uuid).stream()
                .map(mapper::toResource)
                .toList();
    }

    @Override
    public ActivityFormLog save(ActivityFormLog activityForm) {
        return mapper.toApp(jpaRepository.save(mapper.toEntity(activityForm)));
    }
}
