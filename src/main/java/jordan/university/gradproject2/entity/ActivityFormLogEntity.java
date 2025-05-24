package jordan.university.gradproject2.entity;

import jakarta.persistence.*;
import jordan.university.gradproject2.entity.base.BaseEntity;
import jordan.university.gradproject2.enums.Occupation;
import jordan.university.gradproject2.enums.Status;
import jordan.university.gradproject2.enums.WorkflowAction;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ACTIVITY_FORM_LOG")
@Data
public class ActivityFormLogEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "FORM_ID")
    private ActivityFormEntity form;

    @Enumerated(EnumType.STRING)
    @Column(name = "FROM_STATUS")
    private Status fromStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "TO_STATUS")
    private Status toStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACTION")
    private WorkflowAction action;

    @Column(name = "PERFORMED_BY")
    private String performedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "PERFORMED_BY_ROLE")
    private Occupation performedByRole;

    @Column(name = "TIMESTAMP")
    private LocalDateTime timestamp;
}
