package jordan.university.gradproject2.model;

import jordan.university.gradproject2.enums.Status;
import jordan.university.gradproject2.enums.WorkflowAction;
import jordan.university.gradproject2.taskcatalog.TaskCatalog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityForm extends WorkflowProcess<Status, WorkflowAction> {
    private String id;
    private User student;
    private String supervisorName;
    private String activityType;
    private LocalDate activityDate;
    private String organizingEntity;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String phoneNumber;
    private String description;
    private TaskCatalog taskCatalog;

    public void run() {
        taskCatalog.run(this, getWorkflowAction());
    }
}