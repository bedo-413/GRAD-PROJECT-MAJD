package jordan.university.gradproject2.request;

import jordan.university.gradproject2.enums.Status;
import jordan.university.gradproject2.enums.WorkflowAction;
import jordan.university.gradproject2.model.User;
import jordan.university.gradproject2.model.WorkflowProcess;
import jordan.university.gradproject2.validation.ValidationError;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ActivityFormRequest extends WorkflowProcess<Status, WorkflowAction> {
    private User student;
    private User supervisor;
    private String activityType;
    private String activityDate;
    private String organizingEntity;
    private List<String> requiredServices;
    private String location;
    private String startTime;
    private String endTime;
    private String phoneNumber;
    private String description;
    private String rejectionReason;
    private List<ValidationError> errors;
    private boolean isPassThrough;
}
