package pg.search.store.infrastructure.common.currency;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MockCurrencyProvider implements CurrencyProvider {
    private final List<String> currencies = List.of(DEFAULT_CURRENCY, "PLN");

    public boolean isInvalidCurrency(final String currency) {
        return !currencies.contains(currency);
    }

    public List<String> getAvailableCurrencies() {
        return currencies;
    }

    public Float getExchangeRate(final String fromCurrency, final String toCurrency) {
        return 1.f;
    }
}