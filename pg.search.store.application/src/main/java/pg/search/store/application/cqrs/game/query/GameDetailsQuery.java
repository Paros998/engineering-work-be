package pg.search.store.application.cqrs.game.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import pg.lib.cqrs.query.Query;

import pg.search.store.domain.game.GameData;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class GameDetailsQuery implements Query<GameData> {
    private UUID gameId;
}
