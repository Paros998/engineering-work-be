package pg.search.store.application.cqrs.user.command.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.command.Command;

import pg.search.store.domain.user.ChangePasswordData;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class UpdateUserPasswordCommand implements Command<Void> {
    private final UUID userId;
    private final ChangePasswordData data;
}
