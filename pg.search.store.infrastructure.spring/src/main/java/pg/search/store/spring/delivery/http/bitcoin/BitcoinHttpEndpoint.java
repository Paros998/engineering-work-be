package pg.search.store.spring.delivery.http.bitcoin;

import lombok.AllArgsConstructor;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pg.lib.cqrs.service.ServiceExecutor;

import pg.search.store.application.cqrs.crypto.bitcoin.query.BitcoinDataQuery;
import pg.search.store.application.cqrs.crypto.bitcoin.query.BitcoinProfitabilityQuery;
import pg.search.store.domain.common.PageResponse;
import pg.search.store.domain.crypto.bitcoin.BitcoinData;
import pg.search.store.domain.crypto.bitcoin.BitcoinProfitByCard;
import pg.search.store.infrastructure.common.pageable.PageMapper;
import pg.search.store.spring.delivery.http.common.HttpCommonHelper;

import java.util.UUID;

@RestController
@RequestMapping(HttpCommonHelper.BITCOIN_PATH)
@AllArgsConstructor
@Tag(name = "Bitcoin")
public class BitcoinHttpEndpoint {
    private final ServiceExecutor serviceExecutor;

    @GetMapping("info")
    BitcoinData getBitcoinData(final @RequestParam(required = false) UUID userId) {
        final var query = BitcoinDataQuery.of(userId);
        return serviceExecutor.executeQuery(query);
    }

    @GetMapping("estimated-profits")
    PageResponse<BitcoinProfitByCard> calculateBitcoinProfitability(
            final @RequestParam(required = false, defaultValue = "1") Integer page,
            final @RequestParam(required = false, defaultValue = "10") Integer pageLimit,
            final @RequestParam(required = false, defaultValue = "desc") String sortDir,
            final @RequestParam(required = false, defaultValue = "bitcoinHashRate") String sortBy,
            final @RequestParam(required = false) UUID userId,
            final @RequestParam(required = false) String searchQuery,
            final @RequestParam Double electricityCost) {
        final var queryString = (searchQuery == null || searchQuery.isBlank()) ? null : searchQuery;
        final var query = BitcoinProfitabilityQuery.of(PageMapper.toPageRequest(page, pageLimit, sortDir, sortBy), electricityCost,
                queryString, userId);
        return serviceExecutor.executeQuery(query);
    }
}