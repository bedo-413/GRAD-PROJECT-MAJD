package jordan.university.gradproject2.job;

import jordan.university.gradproject2.entity.ActivityFormEntity;
import jordan.university.gradproject2.enums.Status;
import jordan.university.gradproject2.repository.activity.ActivityFormJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FormStatusExpirationJobTest {

    @Mock
    private ActivityFormJpaRepository activityFormJpaRepository;

    @InjectMocks
    private FormStatusExpirationJob formStatusExpirationJob;

    @Captor
    private ArgumentCaptor<List<ActivityFormEntity>> formsCaptor;

    private ActivityFormEntity createForm(String status, LocalDateTime lastUpdatedAt, LocalDate createdAt) {
        ActivityFormEntity form = new ActivityFormEntity();
        form.setUuid(UUID.randomUUID().toString());
        form.setStatus(status);
        form.setLastUpdatedAt(lastUpdatedAt);
        form.setCreatedAt(createdAt);
        return form;
    }

    @Test
    void shouldRejectSupervisorReviewFormsOlderThanTwoDays() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threeDaysAgo = now.minusDays(3);
        LocalDate today = LocalDate.now();

        ActivityFormEntity expiredForm = createForm(Status.PENDING_SUPERVISOR_REVIEW.name(), threeDaysAgo, today);

        when(activityFormJpaRepository.findAll(any(Specification.class)))
                .thenReturn(List.of(expiredForm))
                .thenReturn(List.of());

        // When
        formStatusExpirationJob.processExpiredForms();

        // Then
        verify(activityFormJpaRepository, times(2)).findAll(any(Specification.class));
        verify(activityFormJpaRepository, times(2)).saveAll(formsCaptor.capture());

        // Get the last captured value (from processSupervisorReviewForms)
        List<ActivityFormEntity> capturedForms = formsCaptor.getAllValues().get(0);
        assertEquals(1, capturedForms.size());
        assertEquals(Status.SYSTEM_REJECTED.name(), capturedForms.get(0).getStatus());
        assertEquals("Automatically rejected: Form was in PENDING_SUPERVISOR_REVIEW status for more than 2 days",
                capturedForms.get(0).getRejectionReason());
    }

    @Test
    void shouldRejectFacultyAndUnionReviewFormsOlderThanTwoDays() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threeDaysAgo = now.minusDays(3);
        LocalDate today = LocalDate.now();

        ActivityFormEntity expiredFacultyForm = createForm(Status.PENDING_FACULTY_REVIEW.name(), threeDaysAgo, today);
        ActivityFormEntity expiredUnionForm = createForm(Status.PENDING_UNION_REVIEW.name(), threeDaysAgo, today);

        when(activityFormJpaRepository.findAll(any(Specification.class)))
                .thenReturn(List.of())
                .thenReturn(Arrays.asList(expiredFacultyForm, expiredUnionForm));

        // When
        formStatusExpirationJob.processExpiredForms();

        // Then
        verify(activityFormJpaRepository, times(2)).findAll(any(Specification.class));
        verify(activityFormJpaRepository, times(2)).saveAll(formsCaptor.capture());

        // Get the second captured value (from processFacultyAndUnionReviewForms)
        List<ActivityFormEntity> capturedForms = formsCaptor.getAllValues().get(1);
        assertEquals(2, capturedForms.size());
        assertEquals(Status.SYSTEM_REJECTED.name(), capturedForms.get(0).getStatus());
        assertEquals(Status.SYSTEM_REJECTED.name(), capturedForms.get(1).getStatus());
    }

}
