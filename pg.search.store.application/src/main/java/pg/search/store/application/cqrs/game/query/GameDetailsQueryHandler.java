package pg.search.store.application.cqrs.game.query;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.domain.game.GameData;
import pg.search.store.infrastructure.game.GameMapper;
import pg.search.store.infrastructure.game.GameService;

@Service
@AllArgsConstructor
public class GameDetailsQueryHandler implements QueryHandler<GameDetailsQuery, GameData> {
    private final GameService gameService;
    private final GameMapper gameMapper;

    @Override
    public GameData handle(final GameDetailsQuery query) {
        final var game = gameService.getGameById(query.getGameId());

        return gameMapper.toGameData(game);
    }
}
