package pg.search.store.application.cqrs.product.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.query.Query;

import pg.search.store.domain.common.PageRequest;
import pg.search.store.domain.common.PageResponse;
import pg.search.store.domain.game.GamesFilter;
import pg.search.store.domain.product.BasicProductWithPerformance;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class ProductsByGamesQuery implements Query<PageResponse<BasicProductWithPerformance>> {
    private final PageRequest request;
    private final GamesFilter filter;
    private UUID userId;
    private String cacheMeta;


}


