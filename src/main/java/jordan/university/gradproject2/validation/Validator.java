package jordan.university.gradproject2.validation;

import java.util.List;

/**
 * Interface for all validators in the system.
 * Validators are responsible for validating specific aspects of data.
 * 
 * @param <T> The type of object to be validated
 */
public interface Validator<T> {

    /**
     * Validates the given object and returns a list of validation errors.
     * If validation passes, an empty list is returned.
     * 
     * @param object The object to validate
     * @return A list of validation errors, or an empty list if validation passes
     */
    List<ValidationError> validateWithErrors(T object);
}
