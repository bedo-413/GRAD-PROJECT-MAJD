package jordan.university.gradproject2.mapper;

import jordan.university.gradproject2.entity.ActivityFormLogEntity;
import jordan.university.gradproject2.model.ActivityFormLog;
import jordan.university.gradproject2.resource.ActivityFormLogResource;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityFormLogMapper {

    ActivityFormLogResource toResource(ActivityFormLogEntity entity);

    List<ActivityFormLogResource> toResourceList(List<ActivityFormLogEntity> entities);

    ActivityFormLogEntity toEntity(ActivityFormLog model);

    ActivityFormLog toApp(ActivityFormLogEntity entity);
}
