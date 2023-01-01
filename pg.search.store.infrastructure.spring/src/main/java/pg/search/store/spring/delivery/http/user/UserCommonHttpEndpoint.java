package pg.search.store.spring.delivery.http.user;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import pg.lib.cqrs.service.ServiceExecutor;

import pg.search.store.application.cqrs.user.command.UpdateUserCurrencyCommand;
import pg.search.store.application.cqrs.user.command.settings.UpdateUserSettingsCommand;
import pg.search.store.application.cqrs.user.query.settings.UserSettingsQuery;
import pg.search.store.domain.user.UserSettingsData;
import pg.search.store.spring.delivery.http.common.HttpCommonHelper;

import javax.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping(HttpCommonHelper.USER_PATH)
@AllArgsConstructor
@Tag(name = "User-Common")
public class UserCommonHttpEndpoint {
    private final ServiceExecutor serviceExecutor;

    @GetMapping("{userId}/settings")
    public UserSettingsData getUserSettings(final @PathVariable @NonNull UUID userId) {
        return serviceExecutor.executeQuery(UserSettingsQuery.of(userId));
    }

    @PutMapping("{userId}/settings")
    public void updateUserSettings(final @NonNull @PathVariable UUID userId, final @Valid @NonNull @RequestBody UserSettingsData newSettings) {
        serviceExecutor.executeCommand(UpdateUserSettingsCommand.of(userId, newSettings));
    }

    @PutMapping("{userId}/new-currency/{currency}")
    public void updateUserSettings(final @NonNull @PathVariable UUID userId, final @NonNull @PathVariable String currency) {
        serviceExecutor.executeCommand(UpdateUserCurrencyCommand.of(userId, currency));
    }
}