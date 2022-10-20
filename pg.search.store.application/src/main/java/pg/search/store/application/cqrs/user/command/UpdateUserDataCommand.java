package pg.search.store.application.cqrs.user.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.command.Command;

import pg.search.store.domain.user.UpdateUsernameEmailData;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class UpdateUserDataCommand implements Command<Void> {
    private final UUID userId;
    private final UpdateUsernameEmailData data;
}
