package jordan.university.gradproject2.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jordan.university.gradproject2.enums.Status;
import jordan.university.gradproject2.enums.WorkflowAction;
import jordan.university.gradproject2.taskcatalog.TaskCatalog;
import jordan.university.gradproject2.validation.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityForm extends WorkflowProcess<Status, WorkflowAction> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private User student;
    private User supervisor;
    private String activityType;
    private LocalDate activityDate;
    private LocalDate createdAt;
    private String organizingEntity;
    private List<String> requiredServices;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String phoneNumber;
    private String description;
    private String rejectionReason;
    private TaskCatalog taskCatalog;
    private List<EmailNotification> emailNotifications = new ArrayList<>();
    private List<ValidationError> errors;
    private boolean isPassThrough;
    private boolean hasSponsors;
    private List<String> sponsors;
    private List<String> remarks;

    public void run() {
        taskCatalog.run(this, getWorkflowAction());
    }
}
