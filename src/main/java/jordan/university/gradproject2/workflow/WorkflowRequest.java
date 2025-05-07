package jordan.university.gradproject2.workflow;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class WorkflowRequest {
    private Long assigneeId;
    private Long requestId;
    private Long statusId;
    private String comment;
}