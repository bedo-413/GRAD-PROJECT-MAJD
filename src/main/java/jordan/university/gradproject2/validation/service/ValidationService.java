package jordan.university.gradproject2.validation.service;

import jordan.university.gradproject2.validation.ValidationModel;
import jordan.university.gradproject2.validation.ValidationError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for validating objects using different validation models.
 * This service provides methods to validate objects against specific validation models.
 */
@Service
@RequiredArgsConstructor
public class ValidationService {

    /**
     * Validates an object using the specified validation model.
     * 
     * @param object The object to validate
     * @param validationModel The validation model to use
     * @return A list of validation errors, or an empty list if validation passes
     */
    public List<ValidationError> validate(Object object, ValidationModel validationModel) {
        return validationModel.validateWithErrors(object);
    }

    /**
     * Checks if an object is valid according to the specified validation model.
     * 
     * @param object The object to validate
     * @param validationModel The validation model to use
     * @return true if the object is valid, false otherwise
     */
    public boolean checkValidity(Object object, ValidationModel validationModel) {
        List<ValidationError> errors = validate(object, validationModel);
        return errors.isEmpty();
    }
}
