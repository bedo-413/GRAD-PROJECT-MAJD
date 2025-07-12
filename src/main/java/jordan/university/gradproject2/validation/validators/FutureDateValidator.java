package jordan.university.gradproject2.validation.validators;

import jordan.university.gradproject2.model.ActivityForm;
import jordan.university.gradproject2.validation.ValidationError;
import jordan.university.gradproject2.validation.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Validator that checks if an activity form's activity date is at least 2 weeks from now.
 * This ensures that activities are planned with sufficient advance notice.
 * <p>
 * This class is designed to be extended. Subclasses can override the getMinimumWeeksInAdvance method
 * to change the minimum number of weeks required, or override the validateDateRange method
 * to implement custom date validation logic.
 */
public class FutureDateValidator implements Validator<ActivityForm> {

    protected static final String FIELD_NAME = "activityDate";
    protected static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Gets the minimum number of weeks in advance that the activity date must be.
     * Subclasses can override this method to change the minimum weeks requirement.
     *
     * @return the minimum number of weeks in advance
     */
    protected int getMinimumWeeksInAdvance() {
        return 2; // Default is 2 weeks
    }

    /**
     * Gets the current date. This method can be overridden for testing purposes.
     *
     * @return the current date
     */
    protected LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    /**
     * Validates the date range and adds appropriate error messages if validation fails.
     * Subclasses can override this method to implement custom date validation logic.
     *
     * @param activityDate the activity date to validate
     * @param errors       the list of errors to add to if validation fails
     * @return true if validation passes, false otherwise
     */
    protected boolean validateDateRange(LocalDate activityDate, List<ValidationError> errors) {
        LocalDate now = getCurrentDate();
        int minimumWeeks = getMinimumWeeksInAdvance();
        LocalDate minimumDate = now.plusWeeks(minimumWeeks);

        if (activityDate.isBefore(minimumDate)) {
            errors.add(ValidationError.of(FIELD_NAME,
                    "Activity date must be at least " + minimumWeeks +
                            " weeks from now"));

            errors.add(ValidationError.of(FIELD_NAME,
                    "Current minimum date is " + minimumDate.format(DATE_FORMATTER)));

            long daysDifference = minimumDate.toEpochDay() - activityDate.toEpochDay();
            errors.add(ValidationError.of(FIELD_NAME,
                    "Your selected date is " + daysDifference + " days too early"));

            return false;
        }

        return true;
    }

    @Override
    public List<ValidationError> validateWithErrors(ActivityForm form) {
        List<ValidationError> errors = new ArrayList<>();

        if (form == null) {
            errors.add(ValidationError.of("form", "Activity form cannot be null"));
            return errors;
        }

        LocalDate activityDate = form.getActivityDate();
        if (activityDate == null) {
            errors.add(ValidationError.of(FIELD_NAME, "Activity date cannot be null"));
            return errors;
        }

        validateDateRange(activityDate, errors);

        return errors;
    }
}
