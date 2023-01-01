package pg.search.store.infrastructure.common.currency;

import lombok.AllArgsConstructor;

import pg.search.store.infrastructure.common.exception.CurrencyNotExistException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class LocalCurrencyProvider implements CurrencyProvider {
    private static final Map<String, Map<String, Float>> exchangeRatesMap = new HashMap<>() {
        {
            put(DEFAULT_CURRENCY, new HashMap<>() {{
                put(DEFAULT_CURRENCY, 1.f);
                put("PLN", 4.37f);
            }});

            put("PLN", new HashMap<>() {{
                put("PLN", 1.f);
                put(DEFAULT_CURRENCY, 0.23f);
            }});
        }
    };
    private final List<String> currencies = List.of(DEFAULT_CURRENCY, "PLN");

    public boolean isInvalidCurrency(final String currency) {
        return !currencies.contains(currency);
    }

    public List<String> getAvailableCurrencies() {
        return currencies;
    }

    public Float getExchangeRate(final String fromCurrency, final String toCurrency) {
        if (isInvalidCurrency(fromCurrency))
            throw new CurrencyNotExistException(fromCurrency);

        if (isInvalidCurrency(toCurrency))
            throw new CurrencyNotExistException(toCurrency);

        return exchangeRatesMap.get(fromCurrency).get(toCurrency);
    }
}