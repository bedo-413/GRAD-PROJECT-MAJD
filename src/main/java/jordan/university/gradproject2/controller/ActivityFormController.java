package jordan.university.gradproject2.controller;

import jordan.university.gradproject2.entity.ActivityFormEntity;
import jordan.university.gradproject2.enums.WorkflowAction;
import jordan.university.gradproject2.mapper.ActivityFormMapper;
import jordan.university.gradproject2.model.ActivityForm;
import jordan.university.gradproject2.repository.activity.ActivityFormJpaRepository;
import jordan.university.gradproject2.request.ActivityFormRequest;
import jordan.university.gradproject2.resource.ActivityFormLogResource;
import jordan.university.gradproject2.resource.ActivityFormResource;
import jordan.university.gradproject2.service.ActivityFormService;
import jordan.university.gradproject2.service.LinksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final ActivityFormJpaRepository formJpaRepository;
    private final LinksService linksService;

    public ActivityFormController(ActivityFormService activityFormService, ActivityFormMapper activityFormMapper, ActivityFormJpaRepository formJpaRepository, LinksService linksService) {
        this.activityFormService = activityFormService;
        this.activityFormMapper = activityFormMapper;
        this.formJpaRepository = formJpaRepository;
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

    @GetMapping("/uuid/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public ActivityFormResource getByUuid(@PathVariable String uuid) {
        ActivityForm form = activityFormService.findByUuid(uuid);
        return linksService.addLinks(activityFormMapper.toResource(form), ActivityFormController.class, "getByUuid");
    }


    @ResponseStatus(OK)
    @GetMapping("/get-all")
    public List<ActivityFormResource> getAllActivityForms() {
        log.info("Fetching all activity forms");
        return activityFormService.findAll();
    }

    @GetMapping("/paginated")
    @ResponseStatus(HttpStatus.OK)
    public Page<ActivityFormResource> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size
    ) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt"), Sort.Order.asc("id")));
        Page<ActivityFormEntity> entityPage = formJpaRepository.findAll(pageable);
        return entityPage.map(entity -> linksService.addLinks(activityFormMapper.toResource(entity), ActivityFormController.class, "getByUuid"));
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


    @GetMapping("/{uuid}/logs")
    public ResponseEntity<List<ActivityFormLogResource>> getLogs(@PathVariable String uuid) {
        List<ActivityFormLogResource> logs = activityFormService.getLogsByFormUuid(uuid);
        return ResponseEntity.ok(logs);
    }
}
