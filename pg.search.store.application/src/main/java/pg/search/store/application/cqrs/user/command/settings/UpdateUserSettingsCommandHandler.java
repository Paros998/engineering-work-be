package pg.search.store.application.cqrs.user.command.settings;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.infrastructure.user.settings.SettingsService;

@Service
@AllArgsConstructor
public class UpdateUserSettingsCommandHandler implements CommandHandler<UpdateUserSettingsCommand, Void> {
    private final SettingsService settingsService;

    public Void handle(final UpdateUserSettingsCommand command) {
        settingsService.updateUserSettings(command.getUserId(), command.getData());
        return null;
    }
}
