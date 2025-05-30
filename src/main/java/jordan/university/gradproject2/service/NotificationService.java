package jordan.university.gradproject2.service;

import java.util.Map;

public interface NotificationService {
    /**
     * Send a notification with the given template and data
     *
     * @param recipient The recipient's email address
     * @param subject The subject of the notification
     * @param templateName The name of the template to use
     * @param templateData Data to be used in the template
     * @return true if the notification was sent successfully, false otherwise
     */
    boolean sendNotification(String recipient, String subject, String templateName, Map<String, Object> templateData);

    /**
     * Send a simple notification with plain text
     *
     * @param recipient The recipient's email address
     * @param subject The subject of the notification
     * @param content The content of the notification
     * @return true if the notification was sent successfully, false otherwise
     */
    boolean sendSimpleNotification(String recipient, String subject, String content);
}
