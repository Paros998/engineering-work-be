package pg.search.store.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import pg.lib.awsfiles.config.AmazonConfig;
import pg.lib.cqrs.config.CommandQueryAutoConfiguration;

import pg.search.store.spring.configuration.app.*;

@Configuration
@ComponentScan
@Import({
        CorsConfiguration.class,
        SwaggerConfiguration.class,
        PasswordEncoder.class,
        ApplicationSecurityConfiguration.class,
        CacheConfiguration.class,
        CommandQueryAutoConfiguration.class,
        AmazonConfig.class,
        SearchStoreInfrastructureConfiguration.class,
        SearchStoreApplicationConfiguration.class
})
@EnableAsync
public class SearchStoreModuleConfiguration {
}
