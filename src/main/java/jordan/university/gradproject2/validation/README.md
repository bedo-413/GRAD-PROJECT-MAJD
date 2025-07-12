# Validation Framework

This package provides a flexible validation framework that allows you to create and compose validators for any type of object.

## Overview

The validation framework consists of:

1. `Validator<T>` interface - The contract for all validators
2. `ValidationModel` interface - Defines the contract for validation models
3. `GenericValidationModel` class - A generic implementation of ValidationModel that can validate any object
4. `ValidationError` class - Represents a validation error with field name and message
5. `ValidationService` class - A service for validating objects using different validation models

## How to Use

### 1. Create Validators

Implement the `Validator<T>` interface to create custom validators:

```java
public class MyCustomValidator implements Validator<MyObject> {

    @Override
    public List<ValidationError> validateWithErrors(MyObject object) {
        List<ValidationError> errors = new ArrayList<>();

        // Your validation logic here
        if (object == null) {
            errors.add(ValidationError.of("object", "Object cannot be null"));
            return errors;
        }

        // Add more validation checks and error messages
        if (!isValid(object.getSomeField())) {
            errors.add(ValidationError.of("someField", "Field is invalid"));
            errors.add(ValidationError.of("someField", "Additional details about the error"));
        }

        return errors;
    }

    private boolean isValid(String value) {
        // Your validation logic
        return value != null && !value.isEmpty();
    }
}
```

### 2. Create a ValidationModel

Create a `GenericValidationModel` and add validators to it:

```java
GenericValidationModel validationModel = new GenericValidationModel("myValidation");
validationModel
    .addValidator(new MyCustomValidator())
    .addValidator(new AnotherValidator());
```

### 3. Validate Objects

Validate an object using the validation model:

```java
MyObject objectToValidate = new MyObject();

List<ValidationError> errors = validationModel.validateWithErrors(objectToValidate);
if (!errors.isEmpty()) {
    // Handle validation errors with context
    for (ValidationError error : errors) {
        String field = error.getField();
        String message = error.getMessage();
        // Process error
    }
}
```

### 4. Spring Integration

Define validators and validation models as beans:

```java
@Configuration
public class ValidationConfig {

    @Bean
    public Validator<MyObject> myCustomValidator() {
        return new MyCustomValidator();
    }

    @Bean
    public ValidationModel myObjectCreationValidation(
            Validator<MyObject> myCustomValidator) {
        GenericValidationModel validationModel = new GenericValidationModel("creation");
        validationModel.addValidator(myCustomValidator);
        return validationModel;
    }

    @Bean
    public ValidationModel myObjectUpdateValidation(
            Validator<MyObject> myCustomValidator,
            Validator<AnotherObject> anotherValidator) {
        GenericValidationModel validationModel = new GenericValidationModel("update");
        validationModel.addValidators(List.of(myCustomValidator, anotherValidator));
        return validationModel;
    }
}
```

### 5. Use ValidationService

Use the ValidationService to validate objects with different validation models:

```java
@Service
public class MyService {

    private final ValidationService validationService;
    private final ValidationModel creationValidation;
    private final ValidationModel updateValidation;

    @Autowired
    public MyService(
            ValidationService validationService,
            ValidationModel creationValidation,
            ValidationModel updateValidation) {
        this.validationService = validationService;
        this.creationValidation = creationValidation;
        this.updateValidation = updateValidation;
    }

    public List<ValidationError> validateForCreation(MyObject object) {
        return validationService.validate(object, creationValidation);
    }

    public boolean isValidForUpdate(AnotherObject object) {
        return validationService.checkValidity(object, updateValidation);
    }
}
```

### 6. Extending Validators

Validators like FutureDateValidator are designed to be extended:

```java
public class ExtendedFutureDateValidator extends FutureDateValidator {

    @Override
    protected int getMinimumWeeksInAdvance() {
        return 4; // Require 4 weeks instead of the default 2
    }

    @Override
    protected boolean validateDateRange(LocalDate activityDate, List<ValidationError> errors) {
        // Custom validation logic
        boolean baseValidation = super.validateDateRange(activityDate, errors);

        // Additional validation
        if (activityDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            errors.add(ValidationError.of(FIELD_NAME, "Activities cannot be scheduled on Sundays"));
            return false;
        }

        return baseValidation;
    }
}
```

## Built-in Validators

The framework includes built-in validators in the `validators` package:

1. `FutureDateValidator` - Validates that an activity date is at least 2 weeks in the future

## Benefits

- **Composable** - Combine multiple validators to create complex validation rules
- **Reusable** - Create validators once and reuse them across your application
- **Type-safe** - Generic typing ensures type safety
- **Flexible** - Validate any type of object with custom validation logic
- **Informative** - Get detailed error messages for failed validations
- **Field-aware** - Validation errors can be associated with specific fields
- **Multiple contexts** - Create different validation models for different validation contexts
- **Multiple errors** - Each validator can return multiple error messages
- **Extendable** - Validators are designed to be extended for custom validation logic
- **Service-oriented** - Use ValidationService to validate objects with different models
- **Generic** - ValidationModel can validate any type of object
- **No Qualifiers** - No need for @Qualifier annotations in services

## Best Practices

1. Create small, focused validators that validate one aspect of an object
2. Use meaningful error messages that explain why validation failed
3. Group related validators in a ValidationModel
4. Create different ValidationModel instances for different validation contexts
5. Always specify the field name in ValidationError objects when possible
6. Return multiple error messages when appropriate to provide more context
7. Use ValidationService to validate objects with different validation models
8. Extend existing validators when you need similar but slightly different validation logic
9. Use GenericValidationModel to validate any type of object
10. Avoid using @Qualifier annotations in services by using meaningful bean names
