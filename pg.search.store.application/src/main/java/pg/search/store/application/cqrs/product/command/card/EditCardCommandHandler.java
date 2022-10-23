package pg.search.store.application.cqrs.product.command.card;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.application.cqrs.common.RequestMapper;
import pg.search.store.infrastructure.product.card.CardEntity;
import pg.search.store.infrastructure.product.card.CardService;

import java.util.UUID;

@Service
@AllArgsConstructor
public class EditCardCommandHandler implements CommandHandler<EditCardCommand, UUID> {
    private final CardService cardService;

    public UUID handle(final EditCardCommand command) {
        final var data = RequestMapper.toCardData(command);

        CardEntity cardEntity = cardService.tryToEditCard(data);

        return cardEntity.getProductId();
    }
}