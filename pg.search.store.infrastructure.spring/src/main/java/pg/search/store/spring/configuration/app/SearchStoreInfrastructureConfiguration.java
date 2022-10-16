package pg.search.store.spring.configuration.app;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("pg.search.store.infrastructure")
@EnableJpaRepositories("pg.search.store.infrastructure")
@ComponentScan("pg.search.store.infrastructure")
public class SearchStoreInfrastructureConfiguration {
}
