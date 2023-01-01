package pg.search.store.spring.delivery.http.game;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import pg.lib.cqrs.service.ServiceExecutor;

import pg.search.store.application.cqrs.game.command.AddGameCommand;
import pg.search.store.application.cqrs.game.query.GameDetailsQuery;
import pg.search.store.application.cqrs.game.query.GamesWithScoreQuery;
import pg.search.store.domain.common.PageResponse;
import pg.search.store.domain.game.GameData;
import pg.search.store.domain.game.GameWithScore;
import pg.search.store.domain.game.Platform;
import pg.search.store.infrastructure.common.pageable.PageMapper;
import pg.search.store.spring.delivery.http.common.HttpCommonHelper;

import javax.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping(HttpCommonHelper.GAME_PATH)
@AllArgsConstructor
@Tag(name = "Game")
public class GameHttpEndpoint {
    private final ServiceExecutor serviceExecutor;

    @GetMapping("with-score")
    public PageResponse<GameWithScore> getProducts(
            final @RequestParam(required = false, defaultValue = "1") Integer page,
            final @RequestParam(required = false, defaultValue = "10") Integer pageLimit,
            final @RequestParam(required = false, defaultValue = "asc") String sortDir,
            final @RequestParam(required = false, defaultValue = "title") String sortBy,
            final @RequestParam(required = false) String searchTitle,
            final @RequestParam(required = false) String searchPlatform
    ) {
        Platform platform;

        try {
            platform = Platform.valueOf(searchPlatform);
        } catch (IllegalArgumentException | NullPointerException e) {
            platform = null;
        }

        final var query = GamesWithScoreQuery.of(PageMapper.toPageRequest(page, pageLimit, sortDir, sortBy), searchTitle, platform);
        return serviceExecutor.executeQuery(query);
    }

    @GetMapping("{gameId}/details")
    public GameData getGameDetails(final @NonNull @PathVariable UUID gameId) {
        return serviceExecutor.executeQuery(GameDetailsQuery.of(gameId));
    }

    @PostMapping("AddGameCommand")
    public UUID addGame(final @NonNull @Valid @RequestBody AddGameCommand command) {
        return serviceExecutor.executeCommand(command);
    }
}