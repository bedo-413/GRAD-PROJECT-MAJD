package jordan.university.gradproject2.repository.activitylog;

import jordan.university.gradproject2.model.ActivityFormLog;
import jordan.university.gradproject2.resource.ActivityFormLogResource;

import java.util.List;

public interface ActivityFormLogRepository {
    List<ActivityFormLogResource> findByFormUuidOrderByTimestampAsc(String uuid);

    ActivityFormLog save(ActivityFormLog activityForm);
}
