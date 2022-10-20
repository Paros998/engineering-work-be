package pg.search.store.application.cqrs.user.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.command.Command;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class DeleteUserCommand implements Command<Void> {
    private final UUID userId;
}
