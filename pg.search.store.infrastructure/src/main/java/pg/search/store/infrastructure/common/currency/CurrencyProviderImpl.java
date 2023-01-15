package pg.search.store.infrastructure.common.currency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import pg.search.store.infrastructure.common.exception.CurrencyNotExistException;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyProviderImpl implements CurrencyProvider {
    private static final String CACHE_KEY_AND_PROVIDER = "currency";
    private final OkHttpClient httpClient;
    private final CacheManager cacheManager;
    private final ObjectMapper objectMapper;
    private final String apiKey;
    private final String apiUrl;
    private Map<String, Map<String, Float>> exchangeRatesMap = new HashMap<>();
    private Cache exchangeRatesCache;

    public CurrencyProviderImpl(final CacheManager cacheManager, final ObjectMapper objectMapper,
                                @Value("${currency.api.key}") String apiKey, @Value("${currency.api.url}") String apiUrl) {
        this.cacheManager = cacheManager;
        this.httpClient = new OkHttpClient().newBuilder().build();
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    void init() {
        exchangeRatesCache = cacheManager.getCache(CACHE_KEY_AND_PROVIDER);
    }

    @SuppressWarnings("unchecked")
    public Float getExchangeRate(final String fromCurrency, final String toCurrency) {
        if (isInvalidCurrency(fromCurrency))
            throw new CurrencyNotExistException(fromCurrency);

        if (isInvalidCurrency(toCurrency))
            throw new CurrencyNotExistException(toCurrency);

        exchangeRatesMap = exchangeRatesCache.get(CACHE_KEY_AND_PROVIDER, exchangeRatesMap.getClass());

        if (exchangeRatesMap == null) {
            Map<String, Map<String, Float>> newExchangeRatesMap = fetchExchangeRates();

            exchangeRatesMap = newExchangeRatesMap;
            exchangeRatesCache.put(CACHE_KEY_AND_PROVIDER, newExchangeRatesMap);
        }

        return exchangeRatesMap.get(fromCurrency).get(toCurrency);
    }

    private Map<String, Map<String, Float>> fetchExchangeRates() {
        Map<String, Map<String, Float>> newExchangeRatesMap = new HashMap<>();

        final List<String> availableCurrencies = getAvailableCurrencies();

        availableCurrencies.forEach(currency -> {
            String[] symbols = {""};

            availableCurrencies.stream().filter(s -> !s.equals(currency))
                    .forEach(s -> symbols[0] += s + ',');

            Request request = new Request.Builder()
                    .url(apiUrl + "/latest?symbols=" + symbols[0] + "&base=" + currency)
                    .addHeader("apikey", apiKey)
                    .method("GET", null)
                    .build();

            try {
                Response response = httpClient.newCall(request).execute();

                CurrencyResponse currencyRatesResponse = objectMapper.readValue(response.body().string(), CurrencyResponse.class);

                newExchangeRatesMap.put(currencyRatesResponse.getBase(), currencyRatesResponse.getRates());
            } catch (IOException | NullPointerException e) {
                throw new RuntimeException(e);
            }
        });

        return newExchangeRatesMap;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private static class CurrencyResponse {
        private String base;
        private Map<String, Float> rates;
        private String date;
        private Boolean success;
        private Integer timestamp;
    }
}