package jordan.university.gradproject2.taskcatalog;

import jordan.university.gradproject2.enums.WorkflowAction;
import jordan.university.gradproject2.model.ActivityForm;

public interface ExecutionTask {
    //void execute(ActivityForm activityForm);

    void execute(ActivityForm activityForm, WorkflowAction action);
}
