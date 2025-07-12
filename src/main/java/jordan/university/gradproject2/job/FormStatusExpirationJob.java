package jordan.university.gradproject2.job;

import jakarta.transaction.Transactional;
import jordan.university.gradproject2.entity.ActivityFormEntity;
import jordan.university.gradproject2.enums.Status;
import jordan.university.gradproject2.repository.activity.ActivityFormJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FormStatusExpirationJob {

    private final ActivityFormJpaRepository activityFormJpaRepository;

    /**
     * Job that runs daily to check for forms that need to be automatically rejected:
     * 1. Forms in PENDING_SUPERVISOR_REVIEW status for more than 2 days
     * 2. Forms in PENDING_FACULTY_REVIEW or PENDING_UNION_REVIEW status for more than 2 days
     * 
     * Note: Processing of forms older than 14 days has been moved to a separate job (OldFormsExpirationJob)
     * for better performance and resource management.
     */
    @Scheduled(cron = "0 0 0 * * ?") // Run at midnight every day
    @Transactional
    public void processExpiredForms() {
        log.info("Starting form status expiration job");

        // Process forms in PENDING_SUPERVISOR_REVIEW for more than 2 days
        processSupervisorReviewForms();

        // Process forms in PENDING_FACULTY_REVIEW or PENDING_UNION_REVIEW for more than 3 days
        processFacultyAndUnionReviewForms();

        log.info("Completed form status expiration job");
    }

    private void processSupervisorReviewForms() {
        LocalDateTime twoDaysAgo = LocalDateTime.now().minusDays(2);

        Specification<ActivityFormEntity> spec = (root, query, cb) -> 
            cb.and(
                cb.equal(root.get("status"), Status.PENDING_SUPERVISOR_REVIEW.name()),
                cb.lessThan(root.get("lastUpdatedAt"), twoDaysAgo)
            );

        List<ActivityFormEntity> expiredForms = activityFormJpaRepository.findAll(spec);

        for (ActivityFormEntity form : expiredForms) {
            form.setStatus(Status.SYSTEM_REJECTED.name());
            form.setRejectionReason("Automatically rejected: Form was in PENDING_SUPERVISOR_REVIEW status for more than 2 days");
            log.info("Form {} automatically rejected due to expired PENDING_SUPERVISOR_REVIEW status", form.getUuid());
        }

        activityFormJpaRepository.saveAll(expiredForms);
        log.info("Processed {} forms in PENDING_SUPERVISOR_REVIEW status", expiredForms.size());
    }

    private void processFacultyAndUnionReviewForms() {
        LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);

        Specification<ActivityFormEntity> spec = (root, query, cb) -> 
            cb.and(
                cb.or(
                    cb.equal(root.get("status"), Status.PENDING_FACULTY_REVIEW.name()),
                    cb.equal(root.get("status"), Status.PENDING_UNION_REVIEW.name())
                ),
                cb.lessThan(root.get("lastUpdatedAt"), threeDaysAgo)
            );

        List<ActivityFormEntity> expiredForms = activityFormJpaRepository.findAll(spec);

        for (ActivityFormEntity form : expiredForms) {
            form.setStatus(Status.SYSTEM_REJECTED.name());
            form.setRejectionReason("Automatically rejected: Form was in " + form.getStatus() + " status for more than 2 days");
            log.info("Form {} automatically rejected due to expired {} status", form.getUuid(), form.getStatus());
        }

        activityFormJpaRepository.saveAll(expiredForms);
        log.info("Processed {} forms in PENDING_FACULTY_REVIEW or PENDING_UNION_REVIEW status", expiredForms.size());
    }

}
