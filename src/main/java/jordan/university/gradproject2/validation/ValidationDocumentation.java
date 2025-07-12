package jordan.university.gradproject2.validation;

import jordan.university.gradproject2.validation.service.ValidationService;

/**
 * <h1>Validation Framework Documentation</h1>
 * 
 * <p>This class provides documentation for the validation framework. The framework
 * allows you to create and compose validators for any type of object.</p>
 * 
 * <h2>Overview</h2>
 * 
 * <p>The validation framework consists of:</p>
 * <ol>
 *   <li>{@link Validator} interface - The contract for all validators</li>
 *   <li>{@link ValidationModel} interface - Defines the contract for validation models</li>
 *   <li>{@link ValidationModelImpl} class - A generic implementation of ValidationModel that can validate any object</li>
 *   <li>{@link ValidationError} class - Represents a validation error with field name and message</li>
 *   <li>{@link ValidationService} class - A service for validating objects using different validation models</li>
 * </ol>
 * 
 * <h2>How to Use</h2>
 * 
 * <p>The validation framework can be used in two ways:</p>
 * <ol>
 *   <li>As a standalone framework with manually added validators</li>
 *   <li>As a Spring-integrated framework with validators and validation models defined as beans</li>
 * </ol>
 * 
 * <h3>Option 1: Standalone Usage</h3>
 * 
 * <h4>1. Create Validators</h4>
 * 
 * <p>Implement the {@link Validator} interface to create custom validators:</p>
 * 
 * <pre>
 * public class MyCustomValidator implements Validator&lt;MyObject&gt; {
 *     
 *     &#64;Override
 *     public List&lt;ValidationError&gt; validateWithErrors(MyObject object) {
 *         List&lt;ValidationError&gt; errors = new ArrayList&lt;&gt;();
 *         
 *         // Your validation logic here
 *         if (object == null) {
 *             errors.add(ValidationError.of("object", "Object cannot be null"));
 *             return errors;
 *         }
 *         
 *         // Add more validation checks and error messages
 *         if (!isValid(object.getSomeField())) {
 *             errors.add(ValidationError.of("someField", "Field is invalid"));
 *             errors.add(ValidationError.of("someField", "Additional details about the error"));
 *         }
 *         
 *         return errors;
 *     }
 *     
 *     private boolean isValid(String value) {
 *         // Your validation logic
 *         return value != null && !value.isEmpty();
 *     }
 * }
 * </pre>
 * 
 * <h4>2. Create a ValidationModel</h4>
 * 
 * <p>Create a {@link ValidationModelImpl} and add validators to it:</p>
 * 
 * <pre>
 * GenericValidationModel validationModel = new GenericValidationModel("myValidation");
 * validationModel
 *     .addValidator(new MyCustomValidator())
 *     .addValidator(new AnotherValidator());
 * </pre>
 * 
 * <h4>3. Validate Objects</h4>
 * 
 * <p>Validate an object using the validation model:</p>
 * 
 * <pre>
 * MyObject objectToValidate = new MyObject();
 * 
 * List&lt;ValidationError&gt; errors = validationModel.validateWithErrors(objectToValidate);
 * if (!errors.isEmpty()) {
 *     // Handle validation errors with context
 *     for (ValidationError error : errors) {
 *         String field = error.getField();
 *         String message = error.getMessage();
 *         // Process error
 *     }
 * }
 * </pre>
 * 
 * <h3>Option 2: Spring Integration</h3>
 * 
 * <h4>1. Define Validators and ValidationModels as Beans</h4>
 * 
 * <p>Create a configuration class to define validators and validation models as beans:</p>
 * 
 * <pre>
 * &#64;Configuration
 * public class ValidationConfig {
 *     
 *     &#64;Bean
 *     public Validator&lt;MyObject&gt; myCustomValidator() {
 *         return new MyCustomValidator();
 *     }
 *     
 *     &#64;Bean
 *     public ValidationModel myObjectCreationValidation(
 *             Validator&lt;MyObject&gt; myCustomValidator) {
 *         GenericValidationModel validationModel = new GenericValidationModel("creation");
 *         validationModel.addValidator(myCustomValidator);
 *         return validationModel;
 *     }
 *     
 *     &#64;Bean
 *     public ValidationModel myObjectUpdateValidation(
 *             Validator&lt;MyObject&gt; myCustomValidator,
 *             Validator&lt;AnotherObject&gt; anotherValidator) {
 *         GenericValidationModel validationModel = new GenericValidationModel("update");
 *         validationModel.addValidators(List.of(myCustomValidator, anotherValidator));
 *         return validationModel;
 *     }
 * }
 * </pre>
 * 
 * <h4>2. Use ValidationService</h4>
 * 
 * <p>Use the ValidationService to validate objects with different validation models:</p>
 * 
 * <pre>
 * &#64;Service
 * public class MyService {
 *     
 *     private final ValidationService validationService;
 *     private final ValidationModel creationValidation;
 *     private final ValidationModel updateValidation;
 *     
 *     &#64;Autowired
 *     public MyService(
 *             ValidationService validationService,
 *             ValidationModel creationValidation,
 *             ValidationModel updateValidation) {
 *         this.validationService = validationService;
 *         this.creationValidation = creationValidation;
 *         this.updateValidation = updateValidation;
 *     }
 *     
 *     public List&lt;ValidationError&gt; validateForCreation(MyObject object) {
 *         return validationService.validate(object, creationValidation);
 *     }
 *     
 *     public boolean isValidForUpdate(AnotherObject object) {
 *         return validationService.checkValidity(object, updateValidation);
 *     }
 * }
 * </pre>
 * 
 * <h2>Extending Validators</h2>
 * 
 * <p>Validators like FutureDateValidator are designed to be extended:</p>
 * 
 * <pre>
 * public class ExtendedFutureDateValidator extends FutureDateValidator {
 *     
 *     &#64;Override
 *     protected int getMinimumWeeksInAdvance() {
 *         return 4; // Require 4 weeks instead of the default 2
 *     }
 *     
 *     &#64;Override
 *     protected boolean validateDateRange(LocalDate activityDate, List&lt;ValidationError&gt; errors) {
 *         // Custom validation logic
 *         boolean baseValidation = super.validateDateRange(activityDate, errors);
 *         
 *         // Additional validation
 *         if (activityDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
 *             errors.add(ValidationError.of(FIELD_NAME, "Activities cannot be scheduled on Sundays"));
 *             return false;
 *         }
 *         
 *         return baseValidation;
 *     }
 * }
 * </pre>
 * 
 * <h2>Built-in Validators</h2>
 * 
 * <p>The framework includes built-in validators in the {@code validators} package:</p>
 * <ol>
 *   <li>{@code FutureDateValidator} - Validates that an activity date is at least 2 weeks in the future</li>
 * </ol>
 * 
 * <h2>Benefits</h2>
 * 
 * <ul>
 *   <li><strong>Composable</strong> - Combine multiple validators to create complex validation rules</li>
 *   <li><strong>Reusable</strong> - Create validators once and reuse them across your application</li>
 *   <li><strong>Type-safe</strong> - Generic typing ensures type safety</li>
 *   <li><strong>Flexible</strong> - Validate any type of object with custom validation logic</li>
 *   <li><strong>Informative</strong> - Get detailed error messages for failed validations</li>
 *   <li><strong>Field-aware</strong> - Validation errors can be associated with specific fields</li>
 *   <li><strong>Multiple contexts</strong> - Create different validation models for different validation contexts</li>
 *   <li><strong>Multiple errors</strong> - Each validator can return multiple error messages</li>
 *   <li><strong>Extendable</strong> - Validators are designed to be extended for custom validation logic</li>
 *   <li><strong>Service-oriented</strong> - Use ValidationService to validate objects with different models</li>
 *   <li><strong>Generic</strong> - ValidationModel can validate any type of object</li>
 *   <li><strong>No Qualifiers</strong> - No need for @Qualifier annotations in services</li>
 * </ul>
 * 
 * <h2>Best Practices</h2>
 * 
 * <ol>
 *   <li>Create small, focused validators that validate one aspect of an object</li>
 *   <li>Use meaningful error messages that explain why validation failed</li>
 *   <li>Group related validators in a ValidationModel</li>
 *   <li>Create different ValidationModel instances for different validation contexts</li>
 *   <li>Always specify the field name in ValidationError objects when possible</li>
 *   <li>Return multiple error messages when appropriate to provide more context</li>
 *   <li>Use ValidationService to validate objects with different validation models</li>
 *   <li>Extend existing validators when you need similar but slightly different validation logic</li>
 *   <li>Use GenericValidationModel to validate any type of object</li>
 *   <li>Avoid using @Qualifier annotations in services by using meaningful bean names</li>
 * </ol>
 * 
 * @see Validator
 * @see ValidationModel
 * @see ValidationModelImpl
 * @see ValidationError
 * @see ValidationService
 * @see jordan.university.gradproject2.validation.validators.FutureDateValidator
 * @see jordan.university.gradproject2.validation.config.ValidationConfig
 */
public class ValidationDocumentation {
    // This class is for documentation purposes only and does not contain any implementation
    private ValidationDocumentation() {
        // Private constructor to prevent instantiation
    }
}
