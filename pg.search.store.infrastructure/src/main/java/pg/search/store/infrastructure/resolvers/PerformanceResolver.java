package pg.search.store.infrastructure.resolvers;

import pg.search.store.domain.game.GamesFilter;
import pg.search.store.domain.product.Performance;

public interface PerformanceResolver {
    Performance resolveForCards(GamesFilter filter);

    Performance resolveForCpus(GamesFilter filter);

    Performance resolveForPCs(GamesFilter filter);

    Performance resolveForLaptops(GamesFilter filter);
}