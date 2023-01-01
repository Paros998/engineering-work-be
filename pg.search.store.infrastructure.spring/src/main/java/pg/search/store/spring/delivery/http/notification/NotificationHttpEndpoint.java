package pg.search.store.spring.delivery.http.notification;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import pg.lib.cqrs.service.ServiceExecutor;

import pg.search.store.application.cqrs.notification.command.ReadNotificationCommand;
import pg.search.store.application.cqrs.user.query.notifications.UserNotificationsQuery;
import pg.search.store.domain.notification.NotificationData;
import pg.search.store.spring.delivery.http.common.HttpCommonHelper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(HttpCommonHelper.NOTIFICATION_PATH)
@AllArgsConstructor
@Tag(name = "Notification")
public class NotificationHttpEndpoint {
    private final ServiceExecutor serviceExecutor;

    @GetMapping("{userId}")
    public List<NotificationData> getUserNotifications(final @PathVariable @NonNull UUID userId) {
        return serviceExecutor.executeQuery(UserNotificationsQuery.of(userId));
    }

    @PutMapping("{notificationId}/read")
    public void readNotification(final @NonNull @PathVariable UUID notificationId) {
        serviceExecutor.executeCommand(ReadNotificationCommand.of(notificationId));
    }
}