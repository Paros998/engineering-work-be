package pg.search.store.infrastructure.common.currency;

import java.util.List;

public interface CurrencyProvider {
    String USD = "USD";
    String PLN = "PLN";
    String GBP = "GBP";
    String EUR = "EUR";
    String DEFAULT_CURRENCY = USD;

    List<String> AVAILABLE_CURRENCIES = List.of(USD, PLN, GBP, EUR);

    default boolean isInvalidCurrency(String currency) {
        return !AVAILABLE_CURRENCIES.contains(currency);
    }

    default List<String> getAvailableCurrencies() {
        return AVAILABLE_CURRENCIES;
    }

    Float getExchangeRate(String fromCurrency, String toCurrency);

    default Double exchangePrice(String fromCurrency, String toCurrency, Double originalPrice) {
        return getExchangeRate(fromCurrency, toCurrency) * originalPrice;
    }
}
