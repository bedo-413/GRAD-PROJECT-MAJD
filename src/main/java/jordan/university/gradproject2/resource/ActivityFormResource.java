package jordan.university.gradproject2.resource;

import jordan.university.gradproject2.model.WorkflowResource;
import jordan.university.gradproject2.validation.ValidationError;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ActivityFormResource extends WorkflowResource {
    private Long id;
    private UserResource student;
    private UserResource supervisor;
    private String activityType;
    private LocalDate activityDate;
    private String organizingEntity;
    private List<String> requiredServices;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String phoneNumber;
    private String description;
    private String rejectionReason;
    private LocalDate createdAt;
    private List<ValidationError> errors;
    private boolean isPassThrough;
    private boolean hasSponsors;
    private List<String> sponsors;
    private List<String> remarks;
}
