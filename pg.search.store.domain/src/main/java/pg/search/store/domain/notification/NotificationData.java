package pg.search.store.domain.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class NotificationData {
    private UUID notificationId;

    private UUID productId;

    private LocalDateTime notificationTime;

    private String message;

    private String type;

    private Boolean isRead;
}