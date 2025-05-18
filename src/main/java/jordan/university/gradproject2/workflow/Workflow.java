//package jordan.university.gradproject2.workflow;
//
//import jordan.university.gradproject2.entity.ActivityFormEntity;
//import jordan.university.gradproject2.entity.UserEntity;
//import jordan.university.gradproject2.enums.Status;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//@Setter
//@Getter
//@Entity
//@Table(name = "workflows")
//public class Workflow {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "workflow_id")
//    private Long workflowId;
//
//    @ManyToOne
//    @JoinColumn(name = "assignee_id", nullable = false)
//    private UserEntity assignee;
//
//    @ManyToOne
//    @JoinColumn(name = "request_id", nullable = false)
//    private ActivityFormEntity request;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "status", nullable = false)
//    private Status status;
//
//    @Column(name = "action_date")
//    private LocalDateTime actionDate;
//
//    @Column(name = "comment")
//    private String comment;
//}
