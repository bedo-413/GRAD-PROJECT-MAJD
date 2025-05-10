package jordan.university.gradproject2.resource;

import jordan.university.gradproject2.model.User;
import jordan.university.gradproject2.model.WorkflowResource;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class ActivityFormResource extends WorkflowResource {
    private String id;
    private User requester;
    private String activityType;
    private User supervisor;
    private String location;
    private String description;
    private String objectives;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;
}