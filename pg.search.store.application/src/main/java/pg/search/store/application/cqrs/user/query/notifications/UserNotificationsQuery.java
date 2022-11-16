package pg.search.store.application.cqrs.user.query.notifications;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.query.Query;

import pg.search.store.domain.notification.NotificationData;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class UserNotificationsQuery implements Query<List<NotificationData>> {
    private final UUID userId;
}
