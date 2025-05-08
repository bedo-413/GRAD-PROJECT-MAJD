package jordan.university.gradproject2.repository.activity;

import jordan.university.gradproject2.model.ActivityForm;

import java.util.List;

public interface ActivityFormRepository {

    ActivityForm findById(String id);

    void deleteById(String id);

    List<ActivityForm> findAll();

    void save(ActivityForm activityForm);
}