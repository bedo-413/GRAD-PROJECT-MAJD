package jordan.university.gradproject2.entity;

import jakarta.persistence.*;
import jordan.university.gradproject2.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_notifications")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EmailNotificationEntity extends BaseEntity {

    @Column(name = "recipient", nullable = false)
    private String recipient;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "template_name", nullable = false)
    private String templateName;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "status", nullable = false)
    private String status; // PENDING, SENT, FAILED

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_form_id", nullable = false)
    private ActivityFormEntity activityForm;

    @Column(name = "template_data", columnDefinition = "TEXT")
    private String templateData; // JSON representation of template data
}