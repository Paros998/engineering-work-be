package pg.search.store.spring.configuration.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import pg.search.store.infrastructure.common.currency.CurrencyProvider;
import pg.search.store.infrastructure.common.currency.LocalCurrencyProviderImpl;
import pg.search.store.infrastructure.crypto.bitcoin.BitcoinCurrencyProvider;
import pg.search.store.infrastructure.crypto.bitcoin.LocalBitcoinCurrencyProviderImpl;

@Configuration
@Profile({"devlocal", "test", "e2e"})
public class LocalConfiguration {

    @Primary
    @Bean
    public CurrencyProvider localCurrencyProvider() {
        return new LocalCurrencyProviderImpl();
    }

    @Primary
    @Bean
    public BitcoinCurrencyProvider localBitcoinCurrencyProviderImpl() {
        return new LocalBitcoinCurrencyProviderImpl();
    }

}