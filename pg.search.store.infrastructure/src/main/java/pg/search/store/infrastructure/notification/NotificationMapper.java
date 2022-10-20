package pg.search.store.infrastructure.notification;

import lombok.experimental.UtilityClass;

import pg.search.store.domain.notification.NotificationData;

@UtilityClass
public class NotificationMapper {
    public NotificationData toNotificationData(final NotificationEntity entity) {
        return NotificationData.builder()
                .notificationId(entity.getNotificationId())
                .notificationTime(entity.getNotificationTime())
                .productId(entity.getProduct().getProductId())
                .isRead(entity.getIsRead())
                .message(entity.getMessage())
                .type(entity.getType().name())
                .build();
    }
}
