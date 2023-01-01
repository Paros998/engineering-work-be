package pg.search.store.spring.configuration.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import pg.search.store.infrastructure.common.currency.CurrencyProvider;
import pg.search.store.infrastructure.common.currency.LocalCurrencyProvider;

@Configuration
@Profile("devlocal")
public class LocalConfiguration {

    @Primary
    @Bean
    public CurrencyProvider localCurrencyProvider() {
        return new LocalCurrencyProvider();
    }
}