package jordan.university.gradproject2.model;

import jordan.university.gradproject2.enums.Status;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WorkflowResource {
    private String uuid;
    private Status status;
    private List<WorkflowActionResource> availableActions = new ArrayList<>();
}
