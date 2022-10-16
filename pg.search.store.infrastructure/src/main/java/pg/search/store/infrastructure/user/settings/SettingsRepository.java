package pg.search.store.infrastructure.user.settings;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SettingsRepository extends JpaRepository<SettingsEntity, UUID> {
}