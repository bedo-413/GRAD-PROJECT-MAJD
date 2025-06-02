package jordan.university.gradproject2.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailNotificationResource {
    private Long id;
    private String recipient;
    private String subject;
    private String templateName;
    private LocalDateTime sentAt;
    private String status;
    private Long activityFormId;
    private String templateData;
}