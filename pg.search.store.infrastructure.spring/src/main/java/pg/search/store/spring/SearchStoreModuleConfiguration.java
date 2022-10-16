package pg.search.store.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import pg.lib.awsfiles.config.AmazonConfig;
import pg.lib.cqrs.config.CommandQueryAutoConfiguration;

import pg.search.store.spring.configuration.app.*;

@Configuration
@ComponentScan
@Import({
        CorsConfiguration.class,
        SwaggerConfiguration.class,
        PasswordEncoder.class,
        ApplicationSecurityConfig.class,
        CommandQueryAutoConfiguration.class,
        AmazonConfig.class,
        SearchStoreInfrastructureConfiguration.class,
        SearchStoreApplicationConfiguration.class
})
public class SearchStoreModuleConfiguration {
}
