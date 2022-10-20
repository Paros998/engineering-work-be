package pg.search.store.application.cqrs.user.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import pg.lib.cqrs.command.Command;

import pg.search.store.domain.user.Roles;
import pg.search.store.domain.user.UserData;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class CreateUserCommand implements Command<UserData> {
    private String username;
    private String password;
    private String email;
    private Roles role;
}
