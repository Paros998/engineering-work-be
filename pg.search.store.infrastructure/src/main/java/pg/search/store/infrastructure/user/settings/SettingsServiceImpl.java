package pg.search.store.infrastructure.user.settings;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.search.store.domain.user.UserSettingsData;
import pg.search.store.infrastructure.common.exception.EntityNotFoundException;
import pg.search.store.infrastructure.user.UserEntity;

import java.util.UUID;

@Service
@AllArgsConstructor
public class SettingsServiceImpl implements SettingsService {
    private final SettingsRepository settingsRepository;

    public SettingsEntity createUserSettings(final UserEntity user) {

        SettingsEntity settings = SettingsEntity.of(user);

        settingsRepository.save(settings);

        return settings;
    }

    public UserSettingsData getSettingsByUserId(final UUID userId) {
        final var settings = settingsRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException(userId, UserEntity.class)
        );

        return SettingsMapper.toUserSettingsData(settings);
    }

    public void updateUserSettings(final UUID userId, final UserSettingsData newSettings) {
        final var settings = settingsRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException(userId, UserEntity.class)
        );

        settings.update(newSettings);

        settingsRepository.save(settings);
    }
}
