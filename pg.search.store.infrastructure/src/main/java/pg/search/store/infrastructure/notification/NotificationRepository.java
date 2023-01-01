package pg.search.store.infrastructure.notification;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<NotificationEntity, UUID> {
    @Query("select n from NotificationEntity n where n.user.userId = ?1")
    List<NotificationEntity> findByUserId(UUID userId, Sort sort);

    @Transactional
    @Modifying
    @Query("update NotificationEntity n set n.isRead = true where n.notificationId = ?1")
    int setIsReadTrue(UUID notificationId);

}