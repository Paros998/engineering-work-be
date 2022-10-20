package pg.search.store.application.cqrs.user.query.settings;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.domain.user.UserSettingsData;
import pg.search.store.infrastructure.user.settings.SettingsService;

@Service
@AllArgsConstructor
public class GetUserSettingsQueryHandler implements QueryHandler<GetUserSettingsQuery, UserSettingsData> {
    private final SettingsService settingsService;

    public UserSettingsData handle(final GetUserSettingsQuery query) {
        return settingsService.getSettingsByUserId(query.getUserId());
    }
}
