package jordan.university.gradproject2.workflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workflows")
public class WorkflowController {
    private final WorkflowService workflowService;

    @Autowired
    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @GetMapping
    public List<WorkflowResponse> findAll() {
        return workflowService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkflowResponse> findById(@PathVariable Long id) {
        WorkflowResponse response = workflowService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<WorkflowResponse> create(@RequestBody WorkflowRequest request) {
        WorkflowResponse response = workflowService.create(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkflowResponse> update(
            @PathVariable Long id,
            @RequestBody WorkflowRequest request
    ) {
        WorkflowResponse response = workflowService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        workflowService.delete(id);
        return ResponseEntity.noContent().build();
    }
}