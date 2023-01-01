package pg.search.store.spring.delivery.http.common;

import lombok.AllArgsConstructor;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pg.lib.cqrs.service.ServiceExecutor;

import pg.search.store.infrastructure.common.currency.CurrencyProvider;

import java.util.List;

@RestController
@RequestMapping(HttpCommonHelper.COMMON_DATA_PATH)
@AllArgsConstructor
@Tag(name = "Common-Data")
public class CommonDataHttpEndpoint {
    private final ServiceExecutor serviceExecutor;
    private final CurrencyProvider currencyProvider;

    @GetMapping("currencies")
    List<String> getAvailableCurrencies() {
        return currencyProvider.getAvailableCurrencies();
    }
}
