package jordan.university.gradproject2.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailNotificationRequest {
    private String recipient;
    private String subject;
    private String templateName;
    private String templateData; // JSON representation of template data
    private Long activityFormId;
}