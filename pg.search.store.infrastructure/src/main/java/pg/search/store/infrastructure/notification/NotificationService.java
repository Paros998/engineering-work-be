package pg.search.store.infrastructure.notification;

import pg.search.store.domain.notification.NotificationType;

import java.util.UUID;

public interface NotificationService {
    void fireNotifications(NotificationType type, String data);

    void readNotification(UUID notificationId);
}