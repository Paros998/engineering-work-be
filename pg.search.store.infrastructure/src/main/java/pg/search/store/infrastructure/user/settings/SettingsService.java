package pg.search.store.infrastructure.user.settings;

import pg.search.store.infrastructure.user.UserEntity;

public interface SettingsService {
    SettingsEntity createUserSettings(UserEntity user);
}
