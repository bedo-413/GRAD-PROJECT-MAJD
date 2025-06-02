package jordan.university.gradproject2.mapper;

import jordan.university.gradproject2.entity.ActivityFormEntity;
import jordan.university.gradproject2.entity.EmailNotificationEntity;
import jordan.university.gradproject2.model.ActivityForm;
import jordan.university.gradproject2.model.EmailNotification;
import jordan.university.gradproject2.request.ActivityFormRequest;
import jordan.university.gradproject2.resource.ActivityFormResource;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityFormMapper {

    ActivityFormResource toResource(ActivityFormEntity entity);

    ActivityFormResource toResource(ActivityForm model);

    List<ActivityFormResource> toResource(List<ActivityForm> model);

    ActivityForm toModel(ActivityFormRequest request);

    @Mapping(target = "emailNotifications", ignore = true)
    ActivityForm toModel(ActivityFormEntity entity);

    List<ActivityForm> toModel(List<ActivityFormEntity> entity);

    @Mapping(target = "emailNotifications", ignore = true)
    ActivityFormEntity toEntity(ActivityForm model);

    @Mapping(target = "activityForm", ignore = true)
    EmailNotification emailNotificationEntityToEmailNotification(EmailNotificationEntity emailNotificationEntity);

    @Mapping(target = "activityForm", ignore = true)
    EmailNotificationEntity emailNotificationToEmailNotificationEntity(EmailNotification emailNotification);

    // Helper methods to handle lists
    default List<EmailNotification> emailNotificationEntityListToEmailNotificationList(List<EmailNotificationEntity> list) {
        if (list == null) {
            return null;
        }

        List<EmailNotification> result = new ArrayList<>(list.size());
        for (EmailNotificationEntity entity : list) {
            result.add(emailNotificationEntityToEmailNotification(entity));
        }
        return result;
    }

    default List<EmailNotificationEntity> emailNotificationListToEmailNotificationEntityList(List<EmailNotification> list) {
        if (list == null) {
            return null;
        }

        List<EmailNotificationEntity> result = new ArrayList<>(list.size());
        for (EmailNotification notification : list) {
            result.add(emailNotificationToEmailNotificationEntity(notification));
        }
        return result;
    }

    // After mapping methods to set up relationships correctly
    @AfterMapping
    default void setActivityFormInEmailNotifications(@MappingTarget ActivityForm activityForm, ActivityFormEntity entity) {
        if (entity.getEmailNotifications() != null && !entity.getEmailNotifications().isEmpty()) {
            List<EmailNotification> notifications = emailNotificationEntityListToEmailNotificationList(entity.getEmailNotifications());
            for (EmailNotification notification : notifications) {
                notification.setActivityForm(activityForm);
            }
            activityForm.setEmailNotifications(notifications);
        }
    }

    @AfterMapping
    default void setActivityFormEntityInEmailNotificationEntities(@MappingTarget ActivityFormEntity activityFormEntity, ActivityForm model) {
        if (model.getEmailNotifications() != null && !model.getEmailNotifications().isEmpty()) {
            List<EmailNotificationEntity> notificationEntities = emailNotificationListToEmailNotificationEntityList(model.getEmailNotifications());
            for (EmailNotificationEntity notificationEntity : notificationEntities) {
                notificationEntity.setActivityForm(activityFormEntity);
            }
            activityFormEntity.setEmailNotifications(notificationEntities);
        }
    }
}
