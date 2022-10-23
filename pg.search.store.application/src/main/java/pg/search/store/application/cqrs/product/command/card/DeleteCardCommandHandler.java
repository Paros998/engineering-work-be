package pg.search.store.application.cqrs.product.command.card;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.infrastructure.product.card.CardService;

@Service
@AllArgsConstructor
public class DeleteCardCommandHandler implements CommandHandler<DeleteCardCommand, Void> {
    private final CardService cardService;

    public Void handle(final DeleteCardCommand command) {
        cardService.deleteCardById(command.getCardId());
        return null;
    }
}
