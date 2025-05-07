package jordan.university.gradproject2.mapper;

import jordan.university.gradproject2.entity.ActivityFormEntity;
import jordan.university.gradproject2.model.ActivityForm;
import jordan.university.gradproject2.request.ActivityFormRequest;
import jordan.university.gradproject2.resource.ActivityFormResource;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityFormMapper {

    ActivityFormResource toResource(ActivityFormEntity entity);

    ActivityFormResource toResource(ActivityForm model);

    List<ActivityFormResource> toResource(List<ActivityForm> model);

    ActivityForm toModel(ActivityFormRequest request);

    ActivityForm toModel(ActivityFormEntity entity);

    List<ActivityForm> toModel(List<ActivityFormEntity> entity);

    ActivityFormEntity toEntity(ActivityForm model);
}