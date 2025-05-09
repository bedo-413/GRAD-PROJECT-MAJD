package jordan.university.gradproject2.taskcatalog;

import jordan.university.gradproject2.enums.Status;

import java.util.Map;
import java.util.Set;

public class StatusTransitionManager {

    private static final Map<Status, Set<Status>> allowedTransitions = Map.of(
            Status.NEW, Set.of(Status.MODIFICATION, Status.PENDING_SUPERVISOR_REVIEW),
            Status.MODIFICATION, Set.of(Status.PENDING_SUPERVISOR_REVIEW),
            Status.PENDING_SUPERVISOR_REVIEW, Set.of(Status.SUPERVISOR_APPROVED, Status.SUPERVISOR_REJECTED),
            Status.SUPERVISOR_APPROVED, Set.of(Status.PENDING_FACULTY_REVIEW),
            Status.FACULTY_REVIEWED, Set.of(Status.FACULTY_APPROVED, Status.FACULTY_REJECTED),
            Status.FACULTY_APPROVED, Set.of(Status.PENDING_UNION_REVIEW),
            Status.UNION_REVIEWED, Set.of(Status.INVESTMENT_CENTER_REVIEW),
            Status.INVESTMENT_CENTER_REVIEW, Set.of(Status.INVESTMENT_CENTER_APPROVED, Status.INVESTMENT_CENTER_REJECTED),
            Status.INVESTMENT_CENTER_APPROVED, Set.of(Status.PENDING_DEANSHIP_REVIEW),
            Status.PENDING_DEANSHIP_REVIEW, Set.of(Status.DEANSHIP_APPROVED, Status.DEANSHIP_REJECTED)
    );

    public static boolean canTransition(Status from, Status to) {
        return allowedTransitions.getOrDefault(from, Set.of()).contains(to);
    }
}
