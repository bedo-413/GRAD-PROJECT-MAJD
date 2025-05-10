package jordan.university.gradproject2.entity;

import jakarta.persistence.*;
import jordan.university.gradproject2.entity.base.WorkflowProcessEntity;
import jordan.university.gradproject2.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ActivityFormEntity extends WorkflowProcessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private String id;

    @Column(name = "SYS_UUID")
    private String uuid;

    @ManyToOne
    private User requester;

    @ManyToOne
    private String type;

    @ManyToOne
    private User supervisor;

    @ManyToOne
    private String location;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "OBJECTIVES")
    private String objectives;

    @Column(name = "START_TIME", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "END_TIME", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
}