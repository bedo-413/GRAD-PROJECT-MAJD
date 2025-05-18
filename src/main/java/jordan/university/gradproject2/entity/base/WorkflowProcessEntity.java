package jordan.university.gradproject2.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jordan.university.gradproject2.enums.WorkflowAction;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class WorkflowProcessEntity extends BaseEntity {

    @Enumerated(value = EnumType.STRING)
    private WorkflowAction workflowAction;

    @Column(name = "STATUS")
    private String status;

//    @Enumerated(value = EnumType.STRING)
//    private Workflow workflow;

}
