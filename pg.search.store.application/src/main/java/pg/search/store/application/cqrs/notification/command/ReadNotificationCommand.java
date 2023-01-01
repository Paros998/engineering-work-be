package pg.search.store.application.cqrs.notification.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import pg.lib.cqrs.command.Command;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class ReadNotificationCommand implements Command<Void> {
    private UUID notificationId;
}