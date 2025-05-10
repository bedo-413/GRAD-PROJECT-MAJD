package jordan.university.gradproject2.model;

import jordan.university.gradproject2.enums.WorkflowAction;
import lombok.Data;

@Data
public class WorkflowActionResource {
    private String name;
    private String href;
    private WorkflowAction action;
}
