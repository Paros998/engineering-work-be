package pg.search.store.application.cqrs.user.command.avatar;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.infrastructure.user.UserService;

@Service
@AllArgsConstructor
public class DeleteUserAvatarCommandHandler implements CommandHandler<DeleteUserAvatarCommand, Void> {
    private final UserService userService;

    public Void handle(final DeleteUserAvatarCommand command) {
        userService.deleteUserAvatar(command.getUserId());
        return null;
    }
}
