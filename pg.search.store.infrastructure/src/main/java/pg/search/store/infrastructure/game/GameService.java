package pg.search.store.infrastructure.game;

import pg.search.store.domain.game.GameData;
import pg.search.store.domain.game.Platform;
import pg.search.store.infrastructure.common.pageable.SpringPageRequest;
import pg.search.store.infrastructure.common.pageable.SpringPageResponse;

import java.util.UUID;

public interface GameService {
    GameEntity getGameById(UUID gameId);

    SpringPageResponse<GameEntity> findGamesByNameOrPlatform(String searchTitle, Platform searchPlatform, SpringPageRequest request);

    UUID addGame(GameData data);

    GameEntity toGameEntity(GameData data);
}
