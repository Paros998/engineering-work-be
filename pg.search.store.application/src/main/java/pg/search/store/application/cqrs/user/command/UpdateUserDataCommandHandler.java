package pg.search.store.application.cqrs.user.command;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.infrastructure.user.UserService;

@Service
@AllArgsConstructor
public class UpdateUserDataCommandHandler implements CommandHandler<UpdateUserDataCommand, Void> {
    private final UserService userService;

    public Void handle(final UpdateUserDataCommand command) {
        userService.updateUser(command.getUserId(), command.getData());
        return null;
    }
}
