package jordan.university.gradproject2.model;

import jordan.university.gradproject2.entity.ActivityFormEntity;
import jordan.university.gradproject2.enums.Occupation;
import jordan.university.gradproject2.enums.Status;
import jordan.university.gradproject2.enums.WorkflowAction;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityFormLog {
    private ActivityFormEntity form;
    private Status fromStatus;
    private Status toStatus;
    private WorkflowAction action;
    private String performedBy;
    private Occupation performedByRole;
    private LocalDateTime timestamp;
}
