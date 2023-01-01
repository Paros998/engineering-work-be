package pg.search.store.application.cqrs.product.command.console;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.application.cqrs.common.RequestMapper;
import pg.search.store.domain.notification.NotificationType;
import pg.search.store.infrastructure.notification.NotificationService;
import pg.search.store.infrastructure.product.console.ConsoleService;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AddConsoleCommandHandler implements CommandHandler<AddConsoleCommand, UUID> {
    private final ConsoleService consoleService;
    private final NotificationService notificationService;

    @Override
    public UUID handle(final AddConsoleCommand command) {
        final var entity = consoleService.addConsole(RequestMapper.toConsoleData(command));

        notificationService.fireNotifications(NotificationType.NEW_PRODUCT, entity.getProductId().toString());

        return entity.getProductId();
    }
}
