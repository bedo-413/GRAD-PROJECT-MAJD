package jordan.university.gradproject2.config;

import jordan.university.gradproject2.contract.GlobalConfiguration;
import jordan.university.gradproject2.contract.GlobalConfigurationImpl;
import jordan.university.gradproject2.repository.appconfig.AppConfigRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContractConfig {

    @Bean
    public GlobalConfiguration globalConfiguration(AppConfigRepository appConfigRepository) {
        return new GlobalConfigurationImpl(appConfigRepository);
    }
}
