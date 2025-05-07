package jordan.university.gradproject2.controller;

import jordan.university.gradproject2.model.ActivityForm;
import jordan.university.gradproject2.request.ActivityFormRequest;
import jordan.university.gradproject2.resource.ActivityFormResource;
import jordan.university.gradproject2.service.ActivityFormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity-forms")
@Slf4j
public class ActivityFormController {

    private final ActivityFormService activityFormService;

    public ActivityFormController(ActivityFormService activityFormService) {
        this.activityFormService = activityFormService;
    }

    @GetMapping
    public List<ActivityFormResource> getAllActivityForms() {
        log.info("Fetching all activity forms");
        return activityFormService.findAll();
    }

//    @GetMapping("/{id}") //MIGHT NEED THIS LATER ON -> JUST KEEPING IT COMMENTED UNTIL THEN
//    public ActivityFormResource getActivityFormById(@PathVariable Long id) {
//        log.info("Fetching activity form with id: {}", id);
//        Optional<ActivityFormResource> resource = Optional.ofNullable(activityFormService.find(id));
//
//        return resource.map(ResponseEntity::ok)
//                .orElseGet(() -> {
//                    logger.warn("Activity form with id {} not found", id);
//                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//                });
//    }

    @PostMapping
    public ActivityFormResource createActivityForm(@RequestBody ActivityFormRequest request) {
        return activityFormService.create(request);
    }

    @DeleteMapping
    public void deleteActivityForm(@RequestBody ActivityForm activityForm) {
        activityFormService.delete(activityForm);
    }

//    @PostMapping
//    public ActivityFormResource completeDecision(@RequestBody ActivityForm activityForm) {
//        return activityFormService.completeDecision;
//    }
}
