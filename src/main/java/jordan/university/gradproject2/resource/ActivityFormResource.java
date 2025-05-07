package jordan.university.gradproject2.resource;

import jordan.university.gradproject2.model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityFormResource {
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