package pg.search.store.application.cqrs.user.command.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import pg.lib.cqrs.command.Command;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class ChangeUserStateCommand implements Command<Void> {
    private UUID userId;
}
