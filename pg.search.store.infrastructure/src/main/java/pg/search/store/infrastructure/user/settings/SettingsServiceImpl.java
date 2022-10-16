package pg.search.store.infrastructure.user.settings;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.search.store.infrastructure.user.UserEntity;

@Service
@AllArgsConstructor
public class SettingsServiceImpl implements SettingsService {
    private final SettingsRepository settingsRepository;

    public SettingsEntity createUserSettings(final UserEntity user) {

        SettingsEntity settings = SettingsEntity.of(user);

        settingsRepository.save(settings);

        return settings;
    }
}
