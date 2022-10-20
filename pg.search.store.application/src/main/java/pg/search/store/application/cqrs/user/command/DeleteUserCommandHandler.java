package pg.search.store.application.cqrs.user.command;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.infrastructure.user.UserService;

@Service
@AllArgsConstructor
public class DeleteUserCommandHandler implements CommandHandler<DeleteUserCommand, Void> {
    private final UserService userService;

    public Void handle(final DeleteUserCommand command) {
        userService.deleteUserById(command.getUserId());
        return null;
    }
}
