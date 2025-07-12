package jordan.university.gradproject2.transformer;

import jordan.university.gradproject2.contract.GlobalConfiguration;
import jordan.university.gradproject2.model.ActivityForm;

import java.util.List;

import static java.util.Objects.nonNull;

public class ActivityFormTransformer {

    private final GlobalConfiguration globalConfiguration;

    public ActivityFormTransformer(GlobalConfiguration globalConfiguration) {
        this.globalConfiguration = globalConfiguration;
    }

    public void transform(ActivityForm activityForm) {
        Long studentId = activityForm.getStudent().getId();
        if (studentId == null) {
            throw new IllegalArgumentException("Student ID cannot be null");
        }
        Long supervisorId = activityForm.getSupervisor().getId();
        if (supervisorId == null) {
            throw new IllegalArgumentException("Supervisor ID cannot be null");
        }
        setFormToPassThroughIfTheUserIsPermitted(activityForm);
        setSponsorsIfNotNull(activityForm);
    }

    private void setSponsorsIfNotNull(ActivityForm activityForm) {
        if (nonNull(activityForm.getSponsors()) && !activityForm.getSponsors().isEmpty()) {
            activityForm.setHasSponsors(true);
        }
    }

    private void setFormToPassThroughIfTheUserIsPermitted(ActivityForm activityForm) {
        List<String> permittedUsersForPassThroughForms = globalConfiguration.getPermittedUsers();
        if (!permittedUsersForPassThroughForms.isEmpty()) {
            if (permittedUsersForPassThroughForms.contains(activityForm.getStudent().getUniversityId())) {
                activityForm.setPassThrough(true);
            }
        }
    }
}
