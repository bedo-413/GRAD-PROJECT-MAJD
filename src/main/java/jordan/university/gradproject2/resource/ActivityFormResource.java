package jordan.university.gradproject2.resource;

import jordan.university.gradproject2.model.User;
import jordan.university.gradproject2.model.WorkflowResource;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class ActivityFormResource extends WorkflowResource {
    private Long id;
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
    private LocalDateTime createdAt;
}