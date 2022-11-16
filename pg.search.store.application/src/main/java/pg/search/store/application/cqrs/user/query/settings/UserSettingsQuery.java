package pg.search.store.application.cqrs.user.query.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.query.Query;

import pg.search.store.domain.user.UserSettingsData;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class UserSettingsQuery implements Query<UserSettingsData> {
    private final UUID userId;
}
