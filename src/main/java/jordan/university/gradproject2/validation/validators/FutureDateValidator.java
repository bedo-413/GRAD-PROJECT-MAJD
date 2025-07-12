package jordan.university.gradproject2.validation.validators;

import jordan.university.gradproject2.model.ActivityForm;
import jordan.university.gradproject2.validation.ValidationError;
import jordan.university.gradproject2.validation.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FutureDateValidator implements Validator<ActivityForm> {

    private static final String FIELD_NAME = "activityDate";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<ValidationError> validateWithErrors(ActivityForm form) {
        List<ValidationError> errors = new ArrayList<>();
        LocalDate activityDate = form.getActivityDate();
        if (activityDate == null) {
            errors.add(ValidationError.of(FIELD_NAME, "Activity date cannot be null"));
            return errors;
        }

        validateDateRange(activityDate, errors);

        return errors;
    }

    private void validateDateRange(LocalDate activityDate, List<ValidationError> errors) {
        LocalDate now = LocalDate.now();
        int minimumWeeks = 2;
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

        }
    }
}
