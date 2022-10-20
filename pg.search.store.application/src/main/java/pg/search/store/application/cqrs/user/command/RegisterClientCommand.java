package pg.search.store.application.cqrs.user.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.command.Command;

import pg.search.store.domain.user.RegisterClientData;
import pg.search.store.domain.user.UserData;

@AllArgsConstructor(staticName = "of")
@Getter
public class RegisterClientCommand implements Command<UserData> {
    private final RegisterClientData clientData;
}
