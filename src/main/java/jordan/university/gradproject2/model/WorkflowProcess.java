package jordan.university.gradproject2.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class WorkflowProcess<Status, WorkflowAction> {
    private String uuid;
    private Status status;
    private WorkflowAction workflowAction;
}
