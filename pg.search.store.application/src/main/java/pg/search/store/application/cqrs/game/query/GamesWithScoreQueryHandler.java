package pg.search.store.application.cqrs.game.query;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.domain.common.PageResponse;
import pg.search.store.domain.game.GameWithScore;
import pg.search.store.infrastructure.common.pageable.PageMapper;
import pg.search.store.infrastructure.common.pageable.SpringPageResponse;
import pg.search.store.infrastructure.game.GameEntity;
import pg.search.store.infrastructure.game.GameMapper;
import pg.search.store.infrastructure.game.GameService;

@Service
@AllArgsConstructor
public class GamesWithScoreQueryHandler implements QueryHandler<GamesWithScoreQuery, PageResponse<GameWithScore>> {
    private final GameService gameService;
    private final GameMapper gameMapper;

    @Override
    public PageResponse<GameWithScore> handle(final GamesWithScoreQuery query) {
        SpringPageResponse<GameEntity> games = gameService.findGamesByNameOrPlatform(query.getSearchQuery(), query.getSearchPlatform(),
                PageMapper.toSpringPageRequest(query.getPageRequest()));

        return PageMapper.toPageResponse(games, gameMapper::toGameWithScore, null);
    }
}