package jordan.university.gradproject2.mapper;

import jordan.university.gradproject2.entity.EmailNotificationEntity;
import jordan.university.gradproject2.model.EmailNotification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ActivityFormMapper.class})
public interface EmailNotificationMapper {
    
    @Mapping(source = "activityForm", target = "activityForm")
    EmailNotification toModel(EmailNotificationEntity entity);
    
    @Mapping(source = "activityForm", target = "activityForm")
    EmailNotificationEntity toEntity(EmailNotification model);
}