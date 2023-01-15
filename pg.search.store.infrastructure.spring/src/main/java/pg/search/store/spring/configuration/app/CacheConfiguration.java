package pg.search.store.spring.configuration.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfiguration {
    @Autowired
    @Lazy
    private CacheManager cacheManager;

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("filters",
                "performances", "currency", "cryptoCurrency");
    }

    @Scheduled(timeUnit = TimeUnit.MINUTES, fixedRate = 8)
    public void clearCache() {
        Objects.requireNonNull(cacheManager.getCache("filters")).clear();
        Objects.requireNonNull(cacheManager.getCache("performances")).clear();
    }

    @Scheduled(timeUnit = TimeUnit.HOURS, fixedRate = 8)
    public void clearCryptoCache() {
        Objects.requireNonNull(cacheManager.getCache("currency")).clear();
        Objects.requireNonNull(cacheManager.getCache("cryptoCurrency")).clear();
    }
}
