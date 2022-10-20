package pg.search.store.infrastructure.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<NotificationEntity, UUID> {
    @Query("select n from NotificationEntity n where n.user.userId = ?1")
    List<NotificationEntity> findByUserId(UUID userId);
}