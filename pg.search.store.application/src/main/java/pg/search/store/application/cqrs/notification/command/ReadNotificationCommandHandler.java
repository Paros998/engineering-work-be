package pg.search.store.application.cqrs.notification.command;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.infrastructure.notification.NotificationService;

@Service
@AllArgsConstructor
public class ReadNotificationCommandHandler implements CommandHandler<ReadNotificationCommand, Void> {
    private final NotificationService notificationService;

    @Override
    public Void handle(final ReadNotificationCommand command) {
        notificationService.readNotification(command.getNotificationId());
        return null;
    }
}
