//package jordan.university.gradproject2.workflow;
//
//import com.example.Graduation.Project.activity.Activity;
//import com.example.Graduation.Project.activity.ActivityMapper;
//import com.example.Graduation.Project.activity.ActivityRepository;
//import com.example.Graduation.Project.status.Status;
//import com.example.Graduation.Project.status.StatusRepository;
//import com.example.Graduation.Project.user.User;
//import com.example.Graduation.Project.user.UserMapper;
//import com.example.Graduation.Project.user.UserRepository;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//
//@Component
//public class WorkflowMapper {
//    private final UserRepository userRepository;
//    private final ActivityRepository activityRepository;
//    private final StatusRepository statusRepository;
//    private final UserMapper userMapper;
//    private final ActivityMapper activityMapper; // Add this
//
//    public WorkflowMapper(UserRepository userRepository,
//                          ActivityRepository activityRepository,
//                          StatusRepository statusRepository,
//                          UserMapper userMapper,
//                          ActivityMapper activityMapper) { // Add this parameter
//        this.userRepository = userRepository;
//        this.activityRepository = activityRepository;
//        this.statusRepository = statusRepository;
//        this.userMapper = userMapper;
//        this.activityMapper = activityMapper;
//    }
//
//    public Workflow toWorkflow(WorkflowRequest request) {
//        Workflow workflow = new Workflow();
//
//        User assignee = userRepository.findById(request.getAssigneeId())
//                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getAssigneeId()));
//        workflow.setAssignee(assignee);
//
//        Activity activity = activityRepository.findById(request.getRequestId())
//                .orElseThrow(() -> new RuntimeException("Activity not found with id: " + request.getRequestId()));
//        workflow.setRequest(activity);
//
//        Status status = statusRepository.findById(request.getStatusId())
//                .orElseThrow(() -> new RuntimeException("Status not found with id: " + request.getStatusId()));
//        workflow.setStatus(status);
//
//        workflow.setActionDate(LocalDateTime.now());
//        workflow.setComment(request.getComment());
//
//        return workflow;
//    }
//
//    public WorkflowResponse toWorkflowResponse(Workflow workflow) {
//        WorkflowResponse response = new WorkflowResponse();
//        response.setWorkflowId(workflow.getWorkflowId());
//        response.setAssignee(userMapper.toUserResponse(workflow.getAssignee()));
//        response.setRequest(activityMapper.toActivityResponse(workflow.getRequest())); // Use ActivityMapper here
//        response.setStatus(workflow.getStatus());
//        response.setActionDate(workflow.getActionDate());
//        response.setComment(workflow.getComment());
//        return response;
//    }
//}