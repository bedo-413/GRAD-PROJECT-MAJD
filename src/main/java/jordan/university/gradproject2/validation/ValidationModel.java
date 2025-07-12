package jordan.university.gradproject2.validation;

import java.util.List;

/**
 * Interface for validation models that can validate objects.
 * A validation model holds multiple validators and runs them against an object.
 * 
 * Different validation models can be created for different validation contexts,
 * such as creation validation, update validation, etc.
 */
public interface ValidationModel {

    /**
     * Gets the name of this validation model.
     * 
     * @return the name of the validation model
     */
    String getName();

    /**
     * Sets the name of this validation model.
     * 
     * @param name the name to set
     */
    void setName(String name);

    /**
     * Gets the list of validation errors from the most recent validation.
     * 
     * @return the list of validation errors
     */
    List<ValidationError> getErrors();

    /**
     * Validates the given object and returns a list of validation errors.
     * 
     * @param object The object to validate
     * @return A list of validation errors, or an empty list if validation passes
     */
    List<ValidationError> validateWithErrors(Object object);

    /**
     * Checks if the validation model has any validators.
     * 
     * @return true if the model has validators, false otherwise
     */
    boolean hasValidators();

    /**
     * Gets the number of validators in this model.
     * 
     * @return the number of validators
     */
    int getValidatorCount();
}
