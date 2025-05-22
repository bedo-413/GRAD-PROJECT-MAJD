package jordan.university.gradproject2.repository.activity;

import jordan.university.gradproject2.model.ActivityForm;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ActivityFormRepository {

    Optional<ActivityForm> findByUuid(String uuid);

    void deleteByUuid(String uuid);

    List<ActivityForm> findAll();

    Page<ActivityForm> findPaginated(int page, int size);

    ActivityForm save(ActivityForm activityForm);
}