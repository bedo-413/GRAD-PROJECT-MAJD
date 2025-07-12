package jordan.university.gradproject2.entity;

import jakarta.persistence.*;
import jakarta.persistence.Convert;
import jordan.university.gradproject2.converter.StringListConverter;
import jordan.university.gradproject2.entity.base.WorkflowProcessEntity;
import jordan.university.gradproject2.validation.ValidationError;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "activity_forms")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ActivityFormEntity extends WorkflowProcessEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "requester_id", nullable = false)
    private UserEntity student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "supervisor_id", nullable = false)
    private UserEntity supervisor;

    @Column(name = "activity_type", nullable = false)
    private String activityType;

    @Column(name = "activity_date", nullable = false)
    private LocalDate activityDate;

    @Column(name = "organizing_entity", nullable = false)
    private String organizingEntity;

    @Column(name = "required_services", columnDefinition = "TEXT")
    @Convert(converter = StringListConverter.class)
    private List<String> requiredServices;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "rejection_reason", length = 4000)
    private String rejectionReason;

    @OneToMany(mappedBy = "activityForm", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmailNotificationEntity> emailNotifications = new ArrayList<>();

    @Transient
    private List<ValidationError> errors;

    @Column(name = "is_pass_through")
    private boolean isPassThrough;

    @Column(name = "has_sponsors")
    private boolean hasSponsors;

    @Column(name = "sponsors", columnDefinition = "TEXT")
    @Convert(converter = StringListConverter.class)
    private List<String> sponsors;
}
