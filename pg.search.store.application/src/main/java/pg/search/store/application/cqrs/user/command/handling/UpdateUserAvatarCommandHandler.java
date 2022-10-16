package pg.search.store.application.cqrs.user.command.handling;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.application.cqrs.user.command.UpdateUserAvatarCommand;
import pg.search.store.infrastructure.user.UserService;

@Service
@AllArgsConstructor
public class UpdateUserAvatarCommandHandler implements CommandHandler<UpdateUserAvatarCommand, Void> {
    private final UserService userService;

    public Void handle(final UpdateUserAvatarCommand command) {
        userService.updateUserAvatar(command.getUserId(), command.getFile());
        return null;
    }
}
