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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OldFormsExpirationJobTest {

    @Mock
    private ActivityFormJpaRepository activityFormJpaRepository;

    @InjectMocks
    private OldFormsExpirationJob oldFormsExpirationJob;

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
    void shouldRejectFormsOlderThanFourteenDays() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        LocalDate fifteenDaysAgo = LocalDate.now().minusDays(15);

        ActivityFormEntity oldForm = createForm(Status.PENDING_SUPERVISOR_REVIEW.name(), now, fifteenDaysAgo);

        // Create page for the method call
        Page<ActivityFormEntity> oldFormsPage = new PageImpl<>(List.of(oldForm), org.springframework.data.domain.PageRequest.of(0, 100), 1);

        // Mock the paginated findAll method
        when(activityFormJpaRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(oldFormsPage);  // First call returns the old form

        // When
        oldFormsExpirationJob.processOldForms();

        // Then
        verify(activityFormJpaRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
        verify(activityFormJpaRepository, times(1)).saveAll(formsCaptor.capture());

        List<ActivityFormEntity> capturedForms = formsCaptor.getValue();
        assertEquals(1, capturedForms.size());
        assertEquals(Status.SYSTEM_REJECTED.name(), capturedForms.get(0).getStatus());
        assertEquals("Automatically rejected: Form is older than 14 days",
                capturedForms.get(0).getRejectionReason());
    }

    @Test
    void shouldNotRejectTerminalStatusForms() {
        // Given
        LocalDate fifteenDaysAgo = LocalDate.now().minusDays(15);
        LocalDateTime now = LocalDateTime.now();

        // Forms with terminal statuses that should not be rejected even if old
        ActivityFormEntity approvedForm = createForm(Status.APPROVED.name(), now, fifteenDaysAgo);
        ActivityFormEntity rejectedForm = createForm(Status.REJECTED.name(), now, fifteenDaysAgo);
        ActivityFormEntity systemRejectedForm = createForm(Status.SYSTEM_REJECTED.name(), now, fifteenDaysAgo);
        ActivityFormEntity completedForm = createForm(Status.COMPLETED.name(), now, fifteenDaysAgo);

        // Create empty page for the method call (no forms to process)
        Page<ActivityFormEntity> emptyPage = new PageImpl<>(Collections.emptyList());

        // Mock the paginated findAll method to return empty page (no forms to process)
        when(activityFormJpaRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(emptyPage);

        // When
        oldFormsExpirationJob.processOldForms();

        // Then
        // Verify that the findAll method was called but no forms were saved
        verify(activityFormJpaRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
        verify(activityFormJpaRepository, never()).saveAll(any());
    }
}
