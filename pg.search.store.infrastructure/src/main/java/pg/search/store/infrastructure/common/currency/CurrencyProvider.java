package pg.search.store.infrastructure.common.currency;

import java.util.List;

public interface CurrencyProvider {
    String DEFAULT_CURRENCY = "USD";

    boolean isInvalidCurrency(String currency);

    List<String> getAvailableCurrencies();

    Float getExchangeRate(String fromCurrency, String toCurrency);

    default Double exchangePrice(String fromCurrency, String toCurrency, Double originalPrice) {
        return getExchangeRate(fromCurrency, toCurrency) * originalPrice;
    }
}
