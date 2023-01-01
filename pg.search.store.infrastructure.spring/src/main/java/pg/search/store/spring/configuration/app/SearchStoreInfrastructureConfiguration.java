package pg.search.store.spring.configuration.app;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EntityScan("pg.search.store.infrastructure")
@EnableJpaRepositories("pg.search.store.infrastructure")
@ComponentScan("pg.search.store.infrastructure")
@Import({LocalConfiguration.class})
public class SearchStoreInfrastructureConfiguration {
    @Bean
    @Primary
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("SearchStore-");
        executor.initialize();
        return executor;
    }
}