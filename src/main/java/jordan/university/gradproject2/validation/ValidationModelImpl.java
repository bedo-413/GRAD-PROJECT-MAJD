package jordan.university.gradproject2.validation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * A generic implementation of ValidationModel that can validate any object.
 * This class allows for composing validation logic from multiple validators.
 * 
 * Different validation models can be created for different validation contexts,
 * such as creation validation, update validation, etc.
 */
@NoArgsConstructor
public class ValidationModelImpl implements ValidationModel {

    /**
     * The name of this validation model, used to identify different validation contexts.
     */
    @Getter
    @Setter
    private String name;

    /**
     * The list of validators in this validation model.
     */
    @Getter
    private final List<Validator<?>> validators = new ArrayList<>();

    /**
     * The list of validation errors from the most recent validation.
     */
    @Getter
    private List<ValidationError> errors = new ArrayList<>();

    /**
     * Creates a new validation model with the given name.
     *
     * @param name The name of the validation model
     */
    public ValidationModelImpl(String name) {
        this.name = name;
    }

    /**
     * Adds a validator to the validation model.
     *
     * @param validator The validator to add
     * @return this ValidationModel instance for method chaining
     */
    public ValidationModelImpl addValidator(Validator<?> validator) {
        validators.add(validator);
        return this;
    }

    /**
     * Adds multiple validators to the validation model.
     *
     * @param validatorList The list of validators to add
     * @return this ValidationModel instance for method chaining
     */
    public ValidationModelImpl addValidators(List<Validator<?>> validatorList) {
        validators.addAll(validatorList);
        return this;
    }

    /**
     * Validates the given object against all validators in this model and returns a list of validation errors.
     * Each validator will only be applied if it can handle the type of the object.
     *
     * @param object The object to validate
     * @return A list of validation errors, or an empty list if validation passes
     */
    @Override
    public List<ValidationError> validateWithErrors(Object object) {
        errors.clear();

        for (Validator<?> validator : validators) {
            try {
                // Use reflection to check if the validator can handle this type of object
                Class<?> validatorType = getValidatorType(validator);
                if (validatorType != null && validatorType.isInstance(object)) {
                    @SuppressWarnings("unchecked")
                    Validator<Object> typedValidator = (Validator<Object>) validator;
                    List<ValidationError> validatorErrors = typedValidator.validateWithErrors(object);
                    if (!validatorErrors.isEmpty()) {
                        errors.addAll(validatorErrors);
                    }
                }
            } catch (Exception e) {
                // If there's an error, add it to the list of errors
                errors.add(ValidationError.of("validation", "Error validating object: " + e.getMessage()));
            }
        }

        return errors;
    }

    /**
     * Gets the type parameter of the Validator interface.
     *
     * @param validator The validator to check
     * @return The type parameter of the Validator interface, or null if it can't be determined
     */
    private Class<?> getValidatorType(Validator<?> validator) {
        try {
            // Get all interfaces implemented by the validator
            Class<?>[] interfaces = validator.getClass().getInterfaces();
            for (Class<?> iface : interfaces) {
                // Check if the interface is Validator
                if (iface.equals(Validator.class)) {
                    // Get the generic type parameter
                    java.lang.reflect.Type[] genericInterfaces = validator.getClass().getGenericInterfaces();
                    for (java.lang.reflect.Type genericInterface : genericInterfaces) {
                        if (genericInterface instanceof java.lang.reflect.ParameterizedType) {
                            java.lang.reflect.ParameterizedType parameterizedType = (java.lang.reflect.ParameterizedType) genericInterface;
                            if (parameterizedType.getRawType().equals(Validator.class)) {
                                java.lang.reflect.Type[] typeArguments = parameterizedType.getActualTypeArguments();
                                if (typeArguments.length > 0 && typeArguments[0] instanceof Class) {
                                    return (Class<?>) typeArguments[0];
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Ignore exceptions and return null
        }
        return null;
    }

    /**
     * Checks if the validation model has any validators.
     *
     * @return true if the model has validators, false otherwise
     */
    @Override
    public boolean hasValidators() {
        return !validators.isEmpty();
    }

    /**
     * Gets the number of validators in this model.
     *
     * @return the number of validators
     */
    @Override
    public int getValidatorCount() {
        return validators.size();
    }
}