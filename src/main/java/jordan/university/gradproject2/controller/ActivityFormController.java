package jordan.university.gradproject2.controller;

import jordan.university.gradproject2.enums.WorkflowAction;
import jordan.university.gradproject2.mapper.ActivityFormMapper;
import jordan.university.gradproject2.model.ActivityForm;
import jordan.university.gradproject2.request.ActivityFormRequest;
import jordan.university.gradproject2.resource.ActivityFormResource;
import jordan.university.gradproject2.service.ActivityFormService;
import jordan.university.gradproject2.service.LinksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static jordan.university.gradproject2.controller.ActivityFormController.ACTIVITY_FORM_URL;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(ACTIVITY_FORM_URL)
@Slf4j
public class ActivityFormController {
    protected final static String ACTIVITY_FORM_URL = "/api/activity-forms";
    private final ActivityFormService activityFormService;
    private final ActivityFormMapper activityFormMapper;
    private final LinksService linksService;

    public ActivityFormController(ActivityFormService activityFormService, ActivityFormMapper activityFormMapper, LinksService linksService) {
        this.activityFormService = activityFormService;
        this.activityFormMapper = activityFormMapper;
        this.linksService = linksService;
    }

    @ResponseStatus(OK)
    @PostMapping("/{uuid}/update-status/{action}")
    public ActivityFormResource transitionFormAndUpdateStatusViaLink(
            @PathVariable String uuid,
            @PathVariable WorkflowAction action
    ) {
        ActivityFormResource activityFormResource = activityFormMapper.toResource(activityFormService.transitionFormAndUpdateStatus(uuid, action));
        return linksService.addLinks(activityFormResource, ActivityFormController.class, "transitionFormAndUpdateStatus");
    }

    @ResponseStatus(OK)
    @GetMapping("/get-all")
    public List<ActivityFormResource> getAllActivityForms() {
        log.info("Fetching all activity forms");
        return activityFormService.findAll();
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public ActivityFormResource createActivityForm(@RequestBody ActivityFormRequest request) {
        ActivityForm activityForm = activityFormMapper.toModel(request);
        ActivityFormResource activityFormResource = activityFormService.create(activityForm);
        return linksService.addLinks(activityFormResource, ActivityFormController.class, "transitionFormAndUpdateStatus");
    }

    @ResponseStatus(OK)
    @DeleteMapping("/delete")
    public void deleteActivityForm(@RequestBody ActivityForm activityForm) {
        activityFormService.delete(activityForm);
    }

//    @PostMapping
//    public ActivityFormResource completeDecision(@RequestBody ActivityForm activityForm) {
//        return activityFormService.completeDecision;
//    }

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
}
