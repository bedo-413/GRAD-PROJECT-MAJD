package jordan.university.gradproject2.job;

import jakarta.transaction.Transactional;
import jordan.university.gradproject2.entity.ActivityFormEntity;
import jordan.university.gradproject2.enums.Status;
import jordan.university.gradproject2.repository.activity.ActivityFormJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * Job responsible for processing old forms (older than 14 days) that haven't reached a terminal status.
 * This job was separated from FormStatusExpirationJob to improve performance and maintainability.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OldFormsExpirationJob {

    private final ActivityFormJpaRepository activityFormJpaRepository;

    /**
     * Job that runs daily to check for forms that are older than 14 days and haven't reached a terminal status.
     * These forms will be automatically rejected.
     */
    @Scheduled(cron = "0 0 1 * * ?") // Run at 1 AM every day (different time than FormStatusExpirationJob to avoid resource contention)
    @Transactional
    public void processOldForms() {
        log.info("Starting old forms expiration job");
        
        LocalDate fourteenDaysAgo = LocalDate.now().minusDays(10);
        
        Specification<ActivityFormEntity> spec = (root, query, cb) -> 
            cb.and(
                cb.lessThan(root.get("createdAt"), fourteenDaysAgo),
                cb.not(
                    cb.or(
                        cb.equal(root.get("status"), Status.APPROVED.name()),
                        cb.equal(root.get("status"), Status.REJECTED.name()),
                        cb.equal(root.get("status"), Status.SYSTEM_REJECTED.name()),
                        cb.equal(root.get("status"), Status.COMPLETED.name())
                    )
                )
            );

        int pageSize = 100; // Process 100 records at a time
        int pageNumber = 0;
        int totalProcessed = 0;

        while (true) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            Page<ActivityFormEntity> page = activityFormJpaRepository.findAll(spec, pageable);
            List<ActivityFormEntity> oldForms = page.getContent();

            if (oldForms.isEmpty()) {
                break;
            }

            for (ActivityFormEntity form : oldForms) {
                form.setStatus(Status.SYSTEM_REJECTED.name());
                form.setRejectionReason("Automatically rejected: Form is older than 14 days");
                log.info("Form {} automatically rejected due to age > 14 days", form.getUuid());
            }

            activityFormJpaRepository.saveAll(oldForms);
            totalProcessed += oldForms.size();

            if (!page.hasNext()) {
                break;
            }

            pageNumber++;
        }

        log.info("Processed {} forms older than 10 days", totalProcessed);
        log.info("Completed old forms expiration job");
    }
}