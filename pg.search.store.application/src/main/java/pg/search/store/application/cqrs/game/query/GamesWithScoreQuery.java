package pg.search.store.application.cqrs.game.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.query.Query;

import pg.search.store.domain.common.PageRequest;
import pg.search.store.domain.common.PageResponse;
import pg.search.store.domain.game.GameWithScore;
import pg.search.store.domain.game.Platform;

@AllArgsConstructor(staticName = "of")
@Getter
public class GamesWithScoreQuery implements Query<PageResponse<GameWithScore>> {
    private final PageRequest pageRequest;
    private final String searchQuery;
    private final Platform searchPlatform;
}
