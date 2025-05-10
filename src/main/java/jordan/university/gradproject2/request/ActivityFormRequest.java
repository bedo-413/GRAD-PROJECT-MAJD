package jordan.university.gradproject2.request;

import jordan.university.gradproject2.enums.Status;
import jordan.university.gradproject2.enums.WorkflowAction;
import jordan.university.gradproject2.model.WorkflowProcess;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ActivityFormRequest extends WorkflowProcess<Status, WorkflowAction> {
    private Long requesterId;
    private Long typeId;
    private Long supervisorId;
    private Long locationId;
    private String description;
    private String objectives;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}