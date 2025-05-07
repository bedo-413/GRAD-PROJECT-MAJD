package jordan.university.gradproject2.workflow;

import com.example.Graduation.Project.activity.Activity;
import com.example.Graduation.Project.status.Status;
import com.example.Graduation.Project.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
@Entity
@Table(name = "worksflow")
public class Workflow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workflow_id")
    private Long workflowId;

    @ManyToOne
    @JoinColumn(name = "assignee_id", nullable = false)
    private User assignee;

    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private Activity request;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @Column(name = "action_date")
    private LocalDateTime actionDate;

    @Column(name = "comment")
    private String comment;
}