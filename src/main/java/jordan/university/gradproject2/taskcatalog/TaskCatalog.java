package jordan.university.gradproject2.taskcatalog;


import jordan.university.gradproject2.enums.Status;
import jordan.university.gradproject2.model.ActivityForm;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static java.util.Objects.nonNull;

public class TaskCatalog {

    protected final Map<Status, Supplier<ExecutionTask>> taskRegistry = new HashMap<>();

    public void run(ActivityForm activityForm) {
        getTask(activityForm.getStatus()).execute(activityForm);
    }

    private ExecutionTask getTask(Status status) {
        Supplier<ExecutionTask> taskSupplier = taskRegistry.get(status);
        if (nonNull(taskSupplier))
            return taskSupplier.get();
        throw new IllegalArgumentException("No task found for status " + status);
    }
}
