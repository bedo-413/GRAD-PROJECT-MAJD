package jordan.university.gradproject2.validation.config;

import jordan.university.gradproject2.model.ActivityForm;
import jordan.university.gradproject2.validation.ValidationModel;
import jordan.university.gradproject2.validation.ValidationModelImpl;
import jordan.university.gradproject2.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for validation framework.
 * This class defines beans for validators and validation models.
 */
@Configuration
public class ValidationConfig {

    /**
     * Creates a validation model for activity form creation.
     * This validation model includes validators that should be applied when creating a new activity form.
     *
     * @param futureDateValidator the future date validator
     * @return a validation model for activity form creation
     */
    @Bean
    public ValidationModel activityFormCreationValidation(
            Validator<ActivityForm> futureDateValidator) {
        ValidationModelImpl validationModel = new ValidationModelImpl("activityFormCreation");
        validationModel.addValidator(futureDateValidator);
        return validationModel;
    }

    /**
     * Creates a validation model for activity form updates.
     * This validation model includes validators that should be applied when updating an existing activity form.
     *
     * @param futureDateValidator the future date validator
     * @return a validation model for activity form updates
     */
    @Bean
    public ValidationModel activityFormUpdateValidation(
            Validator<ActivityForm> futureDateValidator) {
        ValidationModelImpl validationModel = new ValidationModelImpl("activityFormUpdate");
        validationModel.addValidator(futureDateValidator);
        return validationModel;
    }
}
