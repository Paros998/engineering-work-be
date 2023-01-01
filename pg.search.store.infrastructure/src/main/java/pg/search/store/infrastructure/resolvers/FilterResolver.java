package pg.search.store.infrastructure.resolvers;

import pg.lib.filters.specification.SpecificationBuilder;

import pg.search.store.domain.game.GamesFilter;

import java.util.List;

public interface FilterResolver {

    List<SpecificationBuilder> resolveForCards(GamesFilter gamesFilter);

    List<SpecificationBuilder> resolveForConsoles(GamesFilter gamesFilter);

    List<SpecificationBuilder> resolveForCpus(GamesFilter gamesFilter);

    List<SpecificationBuilder> resolveForPcs(GamesFilter gamesFilter);

    List<SpecificationBuilder> resolveForLaptops(GamesFilter gamesFilter);
}