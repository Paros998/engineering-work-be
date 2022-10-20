package pg.search.store.infrastructure.user.settings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface SettingsRepository extends JpaRepository<SettingsEntity, UUID> {
    @Query("select s from SettingsEntity s where s.user.userId = ?1")
    Optional<SettingsEntity> findByUserId(UUID userId);
}