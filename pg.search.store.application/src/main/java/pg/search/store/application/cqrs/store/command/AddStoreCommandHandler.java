package pg.search.store.application.cqrs.store.command;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.application.cqrs.common.RequestMapper;
import pg.search.store.infrastructure.store.StoreService;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AddStoreCommandHandler implements CommandHandler<AddStoreCommand, UUID> {
    private final StoreService storeService;

    @Override
    public UUID handle(final AddStoreCommand command) {
        final var storeData = RequestMapper.toStoreData(command);

        return storeService.addStore(storeData);
    }
}
