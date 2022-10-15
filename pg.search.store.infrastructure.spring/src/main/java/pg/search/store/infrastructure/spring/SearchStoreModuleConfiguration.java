package pg.search.store.infrastructure.spring;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import pg.lib.awsfiles.config.AmazonConfig;
import pg.lib.cqrs.config.CommandQueryAutoConfiguration;

import pg.search.store.infrastructure.spring.configuration.app.*;

@Configuration
@EntityScan("pg.search.store.infrastructure")
@EnableJpaRepositories("pg.search.store.infrastructure")
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
