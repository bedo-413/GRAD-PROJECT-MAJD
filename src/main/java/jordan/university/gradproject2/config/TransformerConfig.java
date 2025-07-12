package jordan.university.gradproject2.config;

import jordan.university.gradproject2.contract.GlobalConfiguration;
import jordan.university.gradproject2.transformer.ActivityFormTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TransformerConfig {

    @Bean
    public ActivityFormTransformer activityFormTransformer(GlobalConfiguration globalConfiguration) {
        return new ActivityFormTransformer(globalConfiguration);
    }
}
