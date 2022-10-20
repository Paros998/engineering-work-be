package pg.search.store.application.cqrs.user.command.auth;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.infrastructure.user.UserService;

@Service
@AllArgsConstructor
public class ChangeUserStateCommandHandler implements CommandHandler<ChangeUserStateCommand, Void> {
    private final UserService userService;

    public Void handle(final ChangeUserStateCommand command) {
        userService.changeStateOfUser(command.getUserId());
        return null;
    }
}
