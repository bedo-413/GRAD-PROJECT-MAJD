package jordan.university.gradproject2.repository.activity;

import jordan.university.gradproject2.model.ActivityForm;

import java.util.List;

public interface ActivityFormRepository {

    ActivityForm findByUuid(String uuid);

    void deleteByUuid(String uuid);

    List<ActivityForm> findAll();

    ActivityForm save(ActivityForm activityForm);
}