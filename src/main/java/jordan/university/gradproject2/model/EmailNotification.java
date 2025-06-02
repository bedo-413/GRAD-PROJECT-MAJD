package jordan.university.gradproject2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailNotification {
    private Long id;
    private String recipient;
    private String subject;
    private String templateName;
    private LocalDateTime sentAt;
    private String status; // PENDING, SENT, FAILED
    private ActivityForm activityForm;
    private String templateData; // JSON representation of template data
}