package jordan.university.gradproject2.workflow;

import com.example.Graduation.Project.activity.ActivityResponse;
import com.example.Graduation.Project.status.Status;
import com.example.Graduation.Project.user.UserResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WorkflowResponse {
    private Long workflowId;
    private UserResponse assignee;
    private ActivityResponse request;  // Changed from Activity to ActivityResponse
    private Status status;
    private LocalDateTime actionDate;
    private String comment;
}