package pg.search.store.application.cqrs.user.query.notifications;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.domain.notification.NotificationData;
import pg.search.store.infrastructure.notification.NotificationMapper;
import pg.search.store.infrastructure.notification.NotificationRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UserNotificationsQueryHandler implements QueryHandler<UserNotificationsQuery, List<NotificationData>> {
    private final NotificationRepository notificationRepository;

    public List<NotificationData> handle(final UserNotificationsQuery query) {
        return notificationRepository.findByUserId(query.getUserId(), Sort.by(
                        Sort.Order.desc("notificationTime")
                )).stream()
                .map(NotificationMapper::toNotificationData)
                .toList();
    }
}
