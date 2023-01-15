package pg.search.store.infrastructure.common.currency;

import lombok.AllArgsConstructor;

import pg.search.store.infrastructure.common.exception.CurrencyNotExistException;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class LocalCurrencyProviderImpl implements CurrencyProvider {
    private static final Map<String, Map<String, Float>> exchangeRatesMap = new HashMap<>() {
        {
            put(USD, new HashMap<>() {{
                put(USD, 1.f);
                put(PLN, 4.37f);
                put(GBP, 1.f);
                put(EUR, 1.f);
            }});

            put(PLN, new HashMap<>() {{
                put(PLN, 1.f);
                put(USD, 0.23f);
                put(GBP, 0.23f);
                put(EUR, 0.23f);
            }});

            put(GBP, new HashMap<>() {{
                put(GBP, 1.f);
                put(PLN, 4.37f);
                put(USD, 1.f);
                put(EUR, 1.f);
            }});

            put(EUR, new HashMap<>() {{
                put(EUR, 1.f);
                put(PLN, 4.37f);
                put(GBP, 1.f);
                put(USD, 1.f);
            }});
        }
    };

    public Float getExchangeRate(final String fromCurrency, final String toCurrency) {
        if (isInvalidCurrency(fromCurrency))
            throw new CurrencyNotExistException(fromCurrency);

        if (isInvalidCurrency(toCurrency))
            throw new CurrencyNotExistException(toCurrency);

        return exchangeRatesMap.get(fromCurrency).get(toCurrency);
    }
}