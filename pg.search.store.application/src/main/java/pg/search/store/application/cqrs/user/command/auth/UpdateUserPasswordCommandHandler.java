package pg.search.store.application.cqrs.user.command.auth;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.infrastructure.user.UserService;

@Service
@AllArgsConstructor
public class UpdateUserPasswordCommandHandler implements CommandHandler<UpdateUserPasswordCommand, Void> {
    private final UserService userService;

    public Void handle(final UpdateUserPasswordCommand command) {
        userService.changeUserPassword(command.getUserId(), command.getData());
        return null;
    }
}
