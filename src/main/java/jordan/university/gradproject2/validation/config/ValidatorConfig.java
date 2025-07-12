package jordan.university.gradproject2.validation.config;

import jordan.university.gradproject2.model.ActivityForm;
import jordan.university.gradproject2.validation.Validator;
import jordan.university.gradproject2.validation.validators.FutureDateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfig {

    @Bean
    public Validator<ActivityForm> futureDateValidator() {
        return new FutureDateValidator();
    }
}