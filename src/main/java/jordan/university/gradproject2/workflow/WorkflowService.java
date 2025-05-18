//package jordan.university.gradproject2.workflow;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class WorkflowService {
//    private final WorkflowRepository workflowRepository;
//    private final WorkflowMapper workflowMapper;
//
//    public WorkflowService(WorkflowRepository workflowRepository,
//                           WorkflowMapper workflowMapper) {
//        this.workflowRepository = workflowRepository;
//        this.workflowMapper = workflowMapper;
//    }
//
//    public List<WorkflowResponse> findAll() {
//        return workflowRepository.findAll()
//                .stream()
//                .map(workflowMapper::toWorkflowResponse)
//                .collect(Collectors.toList());
//    }
//
//    public WorkflowResponse findById(Long id) {
//        Workflow workflow = workflowRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Workflow not found with id: " + id));
//        return workflowMapper.toWorkflowResponse(workflow);
//    }
//
//    @Transactional
//    public WorkflowResponse create(WorkflowRequest request) {
//        Workflow workflow = workflowMapper.toWorkflow(request);
//        Workflow savedWorkflow = workflowRepository.save(workflow);
//        return workflowMapper.toWorkflowResponse(savedWorkflow);
//    }
//
//    @Transactional
//    public WorkflowResponse update(Long id, WorkflowRequest request) {
//        Workflow existingWorkflow = workflowRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Workflow not found with id: " + id));
//
//        Workflow updatedWorkflow = workflowMapper.toWorkflow(request);
//        updatedWorkflow.setWorkflowId(id);
//        updatedWorkflow.setActionDate(existingWorkflow.getActionDate());
//
//        Workflow savedWorkflow = workflowRepository.save(updatedWorkflow);
//        return workflowMapper.toWorkflowResponse(savedWorkflow);
//    }
//
//    @Transactional
//    public void delete(Long id) {
//        Workflow workflow = workflowRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Workflow not found with id: " + id));
//        workflowRepository.delete(workflow);
//    }
//}