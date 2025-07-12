package jordan.university.gradproject2.taskcatalog;

import jordan.university.gradproject2.enums.Status;
import jordan.university.gradproject2.enums.WorkflowAction;
import jordan.university.gradproject2.model.ActivityForm;

import java.util.Map;
import java.util.Optional;

import static jordan.university.gradproject2.enums.Status.*;
import static jordan.university.gradproject2.enums.WorkflowAction.APPROVE;

public class StatusTransitionManagerV2 {

    private static final Map<Status, Map<WorkflowAction, Status>> transitions = Map.ofEntries(
            Map.entry(Status.NEW, Map.of(
                    WorkflowAction.APPROVE, Status.PENDING_SUPERVISOR_REVIEW,
                    WorkflowAction.CANCEL, Status.CANCELLED
            )),
            Map.entry(Status.PENDING_SUPERVISOR_REVIEW, Map.of(
                    WorkflowAction.APPROVE, Status.PENDING_FACULTY_REVIEW,
                    WorkflowAction.REJECT, Status.SUPERVISOR_REJECTED
            )),
            Map.entry(Status.PENDING_FACULTY_REVIEW, Map.of(
                    WorkflowAction.APPROVE, PENDING_UNION_REVIEW,
                    WorkflowAction.REJECT, Status.FACULTY_REJECTED
            )),
            Map.entry(PENDING_UNION_REVIEW, Map.of(WorkflowAction.APPROVE, Status.PENDING_DEANSHIP_REVIEW)),
            Map.entry(INVESTMENT_CENTER_REVIEW, Map.of(
                    WorkflowAction.APPROVE, PENDING_DEANSHIP_REVIEW
            )),
            Map.entry(PENDING_DEANSHIP_REVIEW, Map.of(
                    WorkflowAction.APPROVE, Status.DEANSHIP_APPROVED,
                    WorkflowAction.REJECT, Status.DEANSHIP_REJECTED
            )),
            Map.entry(Status.DEANSHIP_APPROVED, Map.of(WorkflowAction.APPROVE, Status.APPROVED)),
            Map.entry(Status.DEANSHIP_REJECTED, Map.of(WorkflowAction.APPROVE, Status.REJECTED))
    );

    public static Optional<Status> getNextStatus(ActivityForm activityForm, Status currentStatus, WorkflowAction action) {
        if (activityForm.isPassThrough())
            return Optional.of(PENDING_DEANSHIP_REVIEW);
        if (PENDING_UNION_REVIEW.equals(currentStatus) && APPROVE.equals(action) && activityForm.isHasSponsors())
            return Optional.of(INVESTMENT_CENTER_REVIEW);

        return Optional.ofNullable(transitions.getOrDefault(currentStatus, Map.of()).get(action));
    }

    public static Map<WorkflowAction, Status> getTransitionsForStatus(Status currentStatus) {
        return transitions.getOrDefault(currentStatus, Map.of());
    }
}
