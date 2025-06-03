package jordan.university.gradproject2.service;

import jordan.university.gradproject2.model.ActivityForm;

public interface NotificationService {
    void sendNotification(String recipient, String subject, ActivityForm activityForm);
}
