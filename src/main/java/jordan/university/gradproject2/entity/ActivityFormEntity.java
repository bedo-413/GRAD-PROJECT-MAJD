package jordan.university.gradproject2.entity;

import jakarta.persistence.*;
import jakarta.persistence.Convert;
import jordan.university.gradproject2.entity.base.WorkflowProcessEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Column(name = "supervisor_name", nullable = false)
    private String supervisorName;

    @Column(name = "activity_type", nullable = false)
    private String activityType;

    @Column(name = "activity_date", nullable = false)
    private LocalDate activityDate;

    @Column(name = "organizing_entity", nullable = false)
    private String organizingEntity;

    @Column(name = "required_services", columnDefinition = "TEXT")
    @Convert(converter = jordan.university.gradproject2.converter.StringListConverter.class)
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
}
