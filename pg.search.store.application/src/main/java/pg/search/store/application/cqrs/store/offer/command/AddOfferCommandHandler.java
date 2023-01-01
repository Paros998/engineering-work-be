package pg.search.store.application.cqrs.store.offer.command;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.application.cqrs.common.RequestMapper;
import pg.search.store.infrastructure.store.offer.OfferService;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AddOfferCommandHandler implements CommandHandler<AddOfferCommand, UUID> {
    private final OfferService offerService;

    @Override
    public UUID handle(final AddOfferCommand command) {
        final var offerData = RequestMapper.toOfferData(command);

        return offerService.addOffer(offerData);
    }
}
