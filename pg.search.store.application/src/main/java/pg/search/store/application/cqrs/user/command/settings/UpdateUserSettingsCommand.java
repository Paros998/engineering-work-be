package pg.search.store.application.cqrs.user.command.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.command.Command;

import pg.search.store.domain.user.UserSettingsData;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class UpdateUserSettingsCommand implements Command<Void> {
    private final UUID userId;
    private final UserSettingsData data;
}
