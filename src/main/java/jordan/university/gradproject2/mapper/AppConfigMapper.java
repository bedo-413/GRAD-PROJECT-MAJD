package jordan.university.gradproject2.mapper;

import jordan.university.gradproject2.entity.AppConfigEntity;
import jordan.university.gradproject2.model.AppConfig;
import jordan.university.gradproject2.request.AppConfigRequest;
import jordan.university.gradproject2.resource.AppConfigResource;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppConfigMapper {

    AppConfigResource toResource(AppConfigEntity entity);

    AppConfigResource toResource(AppConfig model);

    List<AppConfigResource> toResource(List<AppConfig> models);

    AppConfig toModel(AppConfigEntity entity);

    List<AppConfig> toModel(List<AppConfigEntity> entities);

    AppConfigEntity toEntity(AppConfig model);

    AppConfig toModel(AppConfigRequest request);
}
