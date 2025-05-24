package jordan.university.gradproject2.service.logger;

import jordan.university.gradproject2.enums.WorkflowAction;
import jordan.university.gradproject2.model.User;
import jordan.university.gradproject2.security.SecurityUtilService;

public abstract class GenericEntityLogger<E, T> {
    private final SecurityUtilService securityService;

    public GenericEntityLogger(SecurityUtilService securityService) {
        this.securityService = securityService;
    }

    protected User getCurrentUser() {
        return securityService.getCurrentUser();
    }

    public abstract void log(E entity, T from, T to, WorkflowAction action);
}
