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
public class CreateCardCommandHandler implements CommandHandler<CreateCardCommand, UUID> {
    private final CardService cardService;

    public UUID handle(final CreateCardCommand command) {
        final var data = RequestMapper.toCardData(command);

        CardEntity cardEntity = cardService.addCard(data);

        return cardEntity.getProductId();
    }
}
