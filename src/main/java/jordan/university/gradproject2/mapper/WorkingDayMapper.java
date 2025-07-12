package jordan.university.gradproject2.mapper;

import jordan.university.gradproject2.entity.WorkingDayEntity;
import jordan.university.gradproject2.model.WorkingDay;
import jordan.university.gradproject2.resource.WorkingDayResource;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkingDayMapper {

    WorkingDay toModel(WorkingDayEntity entity);

    List<WorkingDay> toModels(List<WorkingDayEntity> entities);

    WorkingDayEntity toEntity(WorkingDay workingDay);

    WorkingDayResource toResource(WorkingDay model);

    List<WorkingDayResource> toResources(List<WorkingDay> models);
}