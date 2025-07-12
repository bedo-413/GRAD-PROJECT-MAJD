package jordan.university.gradproject2.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a validation error that occurs during validation.
 * Contains information about the error, including a message and the field that caused the error.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationError {
    
    /**
     * The name of the field that caused the validation error.
     * This can be null if the error is not related to a specific field.
     */
    private String field;
    
    /**
     * The error message describing the validation failure.
     */
    private String message;
    
    /**
     * Creates a validation error with just a message.
     * 
     * @param message The error message
     * @return A new ValidationError instance
     */
    public static ValidationError of(String message) {
        return new ValidationError(null, message);
    }
    
    /**
     * Creates a validation error with a field name and message.
     * 
     * @param field The field name that caused the error
     * @param message The error message
     * @return A new ValidationError instance
     */
    public static ValidationError of(String field, String message) {
        return new ValidationError(field, message);
    }
}