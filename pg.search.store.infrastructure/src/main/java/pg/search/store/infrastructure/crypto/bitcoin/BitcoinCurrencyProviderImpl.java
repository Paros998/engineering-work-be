package pg.search.store.infrastructure.crypto.bitcoin;

import lombok.AllArgsConstructor;

import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BitcoinCurrencyProviderImpl implements BitcoinCurrencyProvider {
    private final CacheManager cacheManager;

    @Override
    public Float getBitcoinValueInUSD() {
        return 16_611.499f;
    }

    @Override
    public Integer getBitcoinDailyBlockCount() {
        return 144;
    }

    @Override
    public Double getBitcoinNetworkTotalHashRate() {
        return 271_973_650.621;
    }

    @Override
    public Float getBitcoinBlockRevenue() {
        return 6.25f;
    }
}