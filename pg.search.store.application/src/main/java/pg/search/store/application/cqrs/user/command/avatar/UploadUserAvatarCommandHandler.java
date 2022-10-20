package pg.search.store.application.cqrs.user.command.avatar;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.infrastructure.user.UserService;

@Service
@AllArgsConstructor
public class UploadUserAvatarCommandHandler implements CommandHandler<UploadUserAvatarCommand, Void> {
    private final UserService userService;

    public Void handle(final UploadUserAvatarCommand command) {
        userService.uploadUserAvatar(command.getUserId(), command.getFile());
        return null;
    }
}
