package pg.search.store.infrastructure.user.settings;

import pg.search.store.domain.user.UserSettingsData;
import pg.search.store.infrastructure.user.UserEntity;

import java.util.UUID;

public interface SettingsService {
    SettingsEntity createUserSettings(UserEntity user);

    UserSettingsData getSettingsByUserId(UUID userId);

    void updateUserSettings(UUID userId, UserSettingsData newSettings);
}
