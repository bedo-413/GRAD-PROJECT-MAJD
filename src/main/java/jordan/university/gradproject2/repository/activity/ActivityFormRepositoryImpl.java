package jordan.university.gradproject2.repository.activity;

import jordan.university.gradproject2.mapper.ActivityFormMapper;
import jordan.university.gradproject2.model.ActivityForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public class ActivityFormRepositoryImpl implements ActivityFormRepository {

    private final ActivityFormJpaRepository activityFormJpaRepository;
    private final ActivityFormMapper mapper;

    public ActivityFormRepositoryImpl(ActivityFormJpaRepository activityFormJpaRepository,
                                      ActivityFormMapper mapper) {
        this.activityFormJpaRepository = activityFormJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<ActivityForm> findByUuid(String uuid) {
        return activityFormJpaRepository.findByUuid(uuid)
                .map(mapper::toModel);
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

    @Override
    public Page<ActivityForm> findPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt"), Sort.Order.desc("id")));
        return activityFormJpaRepository.findAll(pageable).map(mapper::toModel);
    }
}