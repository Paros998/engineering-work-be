package pg.search.store.application.cqrs.crypto.bitcoin.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.query.Query;

import pg.search.store.domain.common.PageRequest;
import pg.search.store.domain.common.PageResponse;
import pg.search.store.domain.crypto.bitcoin.BitcoinProfitByCard;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class BitcoinProfitabilityQuery implements Query<PageResponse<BitcoinProfitByCard>> {
    private final PageRequest pageRequest;
    private final Double electricityCost;
    private String queryString;
    private UUID userId;
}
