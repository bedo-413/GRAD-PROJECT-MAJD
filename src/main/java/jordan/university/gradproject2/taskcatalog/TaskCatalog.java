package jordan.university.gradproject2.taskcatalog;


import jordan.university.gradproject2.enums.Status;
import jordan.university.gradproject2.enums.WorkflowAction;
import jordan.university.gradproject2.model.ActivityForm;
import jordan.university.gradproject2.repository.activity.ActivityFormRepository;
import jordan.university.gradproject2.taskcatalog.tasks.AutoTransitionTask;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static java.util.Objects.nonNull;

public class TaskCatalog {

    protected final Map<Status, Supplier<ExecutionTask>> taskRegistry = new HashMap<>();

    public TaskCatalog(ActivityFormRepository repository) {
        //taskRegistry.put(Status.SPECIAL_STATUS, () -> new SpecialTask(repository)); //Flexibility for Specialized Tasks: If certain statuses require specialized handling beyond what AutoTransitionTask provides, you can replace the corresponding entries in the taskRegistry with appropriate ExecutionTask implementations. For example:
        for (Status status : Status.values()) {
            taskRegistry.put(status, () -> new AutoTransitionTask(repository));
        }
    }

    public void run(ActivityForm activityForm, WorkflowAction action) {
        getTask(activityForm.getStatus()).execute(activityForm, action);
    }

    private ExecutionTask getTask(Status status) {
        Supplier<ExecutionTask> taskSupplier = taskRegistry.get(status);
        if (nonNull(taskSupplier))
            return taskSupplier.get();
        throw new IllegalArgumentException("No task found for status " + status);
    }
}
