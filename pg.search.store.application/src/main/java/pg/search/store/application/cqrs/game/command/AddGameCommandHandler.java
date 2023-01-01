package pg.search.store.application.cqrs.game.command;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.application.cqrs.common.RequestMapper;
import pg.search.store.infrastructure.game.GameService;
import pg.search.store.infrastructure.product.card.CardService;
import pg.search.store.infrastructure.product.cpu.CpuService;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AddGameCommandHandler implements CommandHandler<AddGameCommand, UUID> {
    private final GameService gameService;
    private final CardService cardService;
    private final CpuService cpuService;

    @Override
    public UUID handle(final AddGameCommand command) {
        cardService.validateCardModels(command.getRequiredGraphicCardModels());
        cpuService.validateProcessorModels(command.getRequiredProcessorModels());

        final var data = RequestMapper.toGameData(command);

        return gameService.addGame(data);
    }
}
