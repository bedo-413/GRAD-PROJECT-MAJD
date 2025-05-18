package jordan.university.gradproject2.service;

import jordan.university.gradproject2.enums.Status;
import jordan.university.gradproject2.enums.WorkflowAction;
import jordan.university.gradproject2.model.WorkflowActionResource;
import jordan.university.gradproject2.model.WorkflowResource;
import jordan.university.gradproject2.request.ActivityFormRequest;
import jordan.university.gradproject2.taskcatalog.StatusTransitionManagerV2;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class LinksService {

    public <T extends WorkflowResource> T addLinks(T resource, Class<?> controllerClass, String methodName) {
        List<WorkflowActionResource> actions = getAvailableActions(resource.getStatus(), resource.getUuid(), controllerClass, methodName);
        resource.setAvailableActions(actions);
        return resource;
    }

    public List<WorkflowActionResource> getAvailableActions(Status currentStatus, String uuid, Class<?> controllerClass, String methodName) {
        Map<WorkflowAction, Status> transitions = StatusTransitionManagerV2.getTransitionsForStatus(currentStatus); //gotta fix the way this works
        return transitions.keySet().stream()
                .map((WorkflowAction action) -> mapToResource(action, uuid, controllerClass, methodName))
                .collect(Collectors.toList());
    }


//    private String generateHref(WorkflowAction action, String uuid, Class<?> controllerClass, String methodName) {
//        try {
//            Object invocationValue = controllerClass
//                    .getMethod(methodName, ActivityFormRequest.class) //error here no such method exception
//                    .invoke(methodOn(controllerClass), uuid, action);
//            return linkTo(invocationValue).toUri().toString();
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to generate href for action: " + action, e);
//        }
//    }

    private WorkflowActionResource mapToResource(WorkflowAction action, String uuid, Class<?> controllerClass, String methodName) {
        String href = String.format("/api/activity-forms/%s/update-status/%s", uuid, action);
        WorkflowActionResource resource = new WorkflowActionResource();
        resource.setAction(action);
        resource.setName(action.name()); // You can customize the display name if needed
        resource.setHref(href);
        return resource;
    }

//    private String generateHref(WorkflowAction action, String uuid) {
//        return WebMvcLinkBuilder.linkTo(
//                WebMvcLinkBuilder.methodOn(ActivityFormController.class)
//                        .transitionForm(uuid, action)
//        ).toUri().toString();
//    }

//    private String generateHref(WorkflowAction action, String uuid, Class<?> controllerClass, String methodName) {
//        try {
//            Method method = controllerClass.getMethod(methodName, String.class, WorkflowAction.class);
//            return linkTo(methodOn(controllerClass).getClass()
//                    .getMethod(methodName, String.class, WorkflowAction.class)
//                    .invoke(null, uuid, action))
//                    .toUri()
//                    .toString();
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to generate href for action: " + action, e);
//        }
//    }
}
