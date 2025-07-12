package jordan.university.gradproject2.taskcatalog;

import jordan.university.gradproject2.enums.Status;
import jordan.university.gradproject2.enums.WorkflowAction;
import jordan.university.gradproject2.model.ActivityForm;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static jordan.university.gradproject2.enums.Status.PENDING_DEANSHIP_REVIEW;

public class StatusTransitionManagerV2 {

    private static final Map<Status, Map<WorkflowAction, Status>> transitions = Map.ofEntries(
            Map.entry(Status.NEW, Map.of(WorkflowAction.APPROVE, Status.PENDING_SUPERVISOR_REVIEW)),
            Map.entry(Status.PENDING_SUPERVISOR_REVIEW, Map.of(
                    WorkflowAction.APPROVE, Status.PENDING_FACULTY_REVIEW,
                    WorkflowAction.REJECT, Status.SUPERVISOR_REJECTED
            )),
            Map.entry(Status.PENDING_FACULTY_REVIEW, Map.of(
                    WorkflowAction.APPROVE, Status.PENDING_UNION_REVIEW,
                    WorkflowAction.REJECT, Status.FACULTY_REJECTED
            )),
            Map.entry(Status.PENDING_UNION_REVIEW, Map.of(WorkflowAction.APPROVE, Status.INVESTMENT_CENTER_REVIEW)),
            Map.entry(Status.INVESTMENT_CENTER_REVIEW, Map.of(
                    WorkflowAction.APPROVE, PENDING_DEANSHIP_REVIEW,
                    WorkflowAction.REJECT, Status.INVESTMENT_CENTER_REJECTED
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
        return Optional.ofNullable(transitions.getOrDefault(currentStatus, Map.of()).get(action));
    }

    public static Map<WorkflowAction, Status> getTransitionsForStatus(Status currentStatus) {
        return transitions.getOrDefault(currentStatus, Map.of());
    }

    public static boolean canTransition(Status from, Status to) {
        return allowedTransitions.getOrDefault(from, List.of()).contains(to);
    }

    public static Optional<Status> getNextStatus(Status current) {
        List<Status> nextList = allowedTransitions.get(current);
        if (nextList != null && !nextList.isEmpty()) {
            return Optional.of(nextList.get(0));
        }
        return Optional.empty();
    }


    private static final Map<Status, List<Status>> allowedTransitions = Map.ofEntries(
            Map.entry(Status.NEW, List.of(Status.PENDING_SUPERVISOR_REVIEW)),
            Map.entry(Status.PENDING_SUPERVISOR_REVIEW, List.of(Status.SUPERVISOR_APPROVED)),
            Map.entry(Status.SUPERVISOR_APPROVED, List.of(Status.PENDING_FACULTY_REVIEW)),
            Map.entry(Status.SUPERVISOR_REJECTED, List.of(Status.REJECTED)),
            Map.entry(Status.FACULTY_APPROVED, List.of(Status.PENDING_UNION_REVIEW)),
            Map.entry(Status.FACULTY_REJECTED, List.of(Status.REJECTED)),
            Map.entry(Status.UNION_REVIEWED, List.of(Status.INVESTMENT_CENTER_REVIEW)), //here i need tasks cuz not all forms need to go to investment center
            Map.entry(Status.INVESTMENT_CENTER_APPROVED, List.of(PENDING_DEANSHIP_REVIEW)),
            Map.entry(Status.INVESTMENT_CENTER_REJECTED, List.of(Status.REJECTED)),
            Map.entry(Status.DEANSHIP_APPROVED, List.of(Status.APPROVED)),
            Map.entry(Status.DEANSHIP_REJECTED, List.of(Status.REJECTED))
    );
}
